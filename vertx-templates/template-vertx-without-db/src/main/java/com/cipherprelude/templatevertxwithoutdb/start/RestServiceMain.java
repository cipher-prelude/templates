package com.cipherprelude.templatevertxwithoutdb.start;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cipherprelude.templatevertxwithoutdb.controller.RestServiceController;
import com.cipherprelude.templatevertxwithoutdb.service.BackendService;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;

public class RestServiceMain {

    private static final Logger LOG = LoggerFactory.getLogger(RestServiceMain.class);

    private final Thread mainThread;
    private final CountDownLatch countDownLatch;

    private boolean daemonize = false;

    private RestServiceMain() {
        this.mainThread = Thread.currentThread();
        this.countDownLatch = new CountDownLatch(1);
    }

    public static void main(String args[]) {
        RestServiceMain main = new RestServiceMain();

        try {
            main.start(args);
        } catch (Throwable e) {
            LOG.error("Error in application", e);
        }
    }

    public void start(String[] args) {

        if ((null != args) && (args.length > 0))
            if (StringUtils.containsIgnoreCase(args[0], "daemon"))
                daemonize = true;
        daemonize();
        addDaemonShutdownHook();

        try {

            RestServiceController restController = new RestServiceController();

            BackendService bs = new BackendService() ; 
            
            restController.setBackendSvc(bs);
            
            
            final Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(100).setMetricsOptions(
                    new DropwizardMetricsOptions().setEnabled(true)));

            vertx.deployVerticle(restController, new DeploymentOptions());

        } catch (Throwable e) {
            e.printStackTrace();
            LOG.debug(getStackTrace(e));
        }

        LOG.info("REST Service Started");
        await();
        LOG.info("REST Service Terminated");
    }



   

    public static String getStackTrace(Throwable excep) {
        StringWriter traceWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(traceWriter, false);
        excep.printStackTrace(printWriter);
        printWriter.close();
        String faultMessage = traceWriter.getBuffer().toString();
        return faultMessage;
    }

    private void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOG.debug("interrupted during runtime");
        }
    }

    private void addDaemonShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.debug("Shutting down");
                countDownLatch.countDown();
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    LOG.debug("interrupted during shutdown");
                }
            }
        });
        LOG.debug("Service shutdown hook installed");
    }

    /**
     * Converts the process into a daemon by closing input, output and error streams For UNIX deployments this
     * effectively converts the process into a daemon. Now the shell that invoked the process can be shutdown without
     * killing the process.
     */
    private void daemonize() {
        if (daemonize) {
            LOG.debug("daemonize");
        }
    }

    public void setDaemonize(boolean daemonize) {
        this.daemonize = daemonize;
    }

}

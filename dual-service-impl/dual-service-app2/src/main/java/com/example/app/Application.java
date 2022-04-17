package com.example.app;

import com.example.rest.ServiceARESTInitialiser;
import com.example.rest.ServiceARestProxy;
import com.example.service.ModuleAInitialiser;
import com.example.service.ModuleBInitialiser;
import com.example.service.ServiceAProxy;
import com.example.service.ServiceB;
import com.example.shared.configuration.ModuleConfig;
import com.example.shared.configuration.PropertyName;
import com.example.shared.factory.ObjectFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {
    private static PropertyName APP_NAME = PropertyName.of("application.name");

    public static void main(String... args) {
        ModuleConfig moduleConfig = ModuleConfig.loadConfiguration();
        String appName = moduleConfig.getStringList(APP_NAME).get(0);
        System.out.println("Application " + appName + " has started.");
        new ModuleAInitialiser().initialise();
        new ModuleBInitialiser().initialise();
        new ServiceARESTInitialiser().initialise();

        //ObjectFactory.createFactory().register(ServiceAProxy.class, ServiceADirectMethodProxy.class);
        ObjectFactory.createFactory().register(ServiceAProxy.class, ServiceARestProxy.class);

        ServiceB serviceB = ObjectFactory.createFactory().createObject(ServiceB.class);
        //only on application 1... not here.
        //    serviceB.doBusiness();


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                buildSwingUI(appName, serviceB);
            }
        });

    }

    private static void buildSwingUI(String appName, ServiceB serviceB) {
        JButton click_me = new JButton(appName);
        click_me.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceB.doBusiness();
            }
        });

        JFrame frame = new JFrame(appName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel(appName);
        frame.getContentPane().add(label);
        frame.getContentPane().add(click_me);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}

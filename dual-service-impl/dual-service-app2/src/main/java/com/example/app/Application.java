package com.example.app;

import com.example.broker.ServiceADirectMethodProxy;
import com.example.rest.ServiceARESTInitialiser;
import com.example.service.ModuleAInitialiser;
import com.example.service.ModuleBInitialiser;
import com.example.service.ServiceAProxy;
import com.example.service.ServiceB;
import com.example.shared.factory.ObjectFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {

    public static final String APP_NAME = "APP2";

    public static void main(String... args) {
        new ModuleAInitialiser().initialise();
        new ModuleBInitialiser().initialise();
        new ServiceARESTInitialiser().initialise();
        ObjectFactory.createFactory().register(ServiceAProxy.class, ServiceADirectMethodProxy.class);
        //ObjectFactory.createFactory().register(ServiceAProxy.class, ServiceARestProxy.class);

        ServiceB serviceB = ObjectFactory.createFactory().createObject(ServiceB.class);
        serviceB.doBusiness();


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                buildSwingUI(serviceB);
            }
        });

    }

    private static void buildSwingUI(ServiceB serviceB) {
        JButton click_me = new JButton("Click me");
        click_me.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceB.doBusiness();
            }
        });

        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
        frame.getContentPane().add(click_me);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}

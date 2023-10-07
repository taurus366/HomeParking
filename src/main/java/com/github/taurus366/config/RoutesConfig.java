package com.github.taurus366.config;

import com.github.taurus366.views.MainLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.parking.system.views.camera.CameraListView;

public class RoutesConfig implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent event) {
        RouteConfiguration.forSessionScope().setRoute("camera_list", CameraListView.class, MainLayout.class);
        if (!event.getSource()
                .getDeploymentConfiguration()
                .isProductionMode()) {
            RouteConfiguration configuration =
                    RouteConfiguration.forApplicationScope();

            configuration.setRoute("camera_list", CameraListView.class, MainLayout.class);
        }
    }
}

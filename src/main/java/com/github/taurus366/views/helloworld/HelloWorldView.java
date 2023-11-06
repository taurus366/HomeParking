package com.github.taurus366.views.helloworld;

import com.github.taurus366.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import jakarta.annotation.security.RolesAllowed;
import org.system.i18n.CustomI18NProvider;

import java.util.Locale;
import java.util.Optional;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
//@PermitAll
@RolesAllowed("ADMIN")
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView() {

        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        CustomI18NProvider provider = new CustomI18NProvider();


//        CREATE TABLES FOR LOCALES
        final String characterEncoding = VaadinService.getCurrentRequest().getCharacterEncoding();
        System.out.println(characterEncoding);
        name = new TextField(provider.getTranslation("greeting",provider.LOCALE_BG, "ALI"));


        add(name, sayHello, provider.getLanguageSelectorBox());
    }

}

package com.parking.www.views.user;

import com.parking.www.model.RoleEnum;
import com.parking.www.model.entity.UserEntity;
import com.parking.www.model.service.UserRepository;
import com.parking.www.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Users list")
@Route(value = "user_list", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UserListView extends VerticalLayout {

    static final String ID = "id";
   static final String USERNAME = "username";
   static final String NAME = "name";
   static final String ROLEENUMS = "roleEnums";
   static final String BTN_EDIT_NAME = "Edit";
   static final String BTN_SAVE_NAME = "Save";
    private final UserRepository userRepository;
    public UserListView(UserRepository userRepository) {
        this.userRepository = userRepository;
        Grid entityGrid = new Grid<>(UserEntity.class, false);
        Editor<UserEntity> editor = entityGrid.getEditor();

        add(new H1("Users List"));

        Button newUserBtn = new Button("add New");
        add(newUserBtn);


//        entityGrid.setColumns(ID,"username","name","roleEnums");

        final Grid.Column columnId = entityGrid.addColumn(ID).setWidth("10px");
        final Grid.Column columnUsername = entityGrid.addColumn(USERNAME);
        final Grid.Column columnName = entityGrid.addColumn(NAME);
        final Grid.Column columnRole = entityGrid.addColumn(ROLEENUMS).setHeader("Roles");


        Grid.Column editColumn = entityGrid.addComponentColumn(person -> {
            Button editButton = new Button(BTN_EDIT_NAME);
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                entityGrid.getEditor().editItem(person);
            });
            return editButton;
        }).setWidth("150px").setFlexGrow(0);

//        final Grid.Column username = entityGrid.getColumnByKey("username");

        Binder<UserEntity> binder = new Binder<>(UserEntity.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        /// USERNAME
        TextField usernameField = new TextField();
        usernameField.setWidthFull();
        binder.forField(usernameField)
                .asRequired("Username name must not be empty")
//                .withStatusLabel(firstNameValidationMessage)
                .bind(UserEntity::getUsername, UserEntity::setUsername);
        columnUsername.setEditorComponent(usernameField);

        /// NAME
        TextField nameField = new TextField();
        nameField.setWidthFull();
        binder.forField(nameField)
                .asRequired("Name name must not be empty")
//                .withStatusLabel(firstNameValidationMessage)
                .bind(UserEntity::getName, UserEntity::setName);
        columnName.setEditorComponent(nameField);

        // ROLES
        MultiSelectComboBox<RoleEnum> rolesBox = new MultiSelectComboBox<>();
        rolesBox.setItems(RoleEnum.values());

        binder.forField(rolesBox)
                .asRequired("Role's must not be empty")
//                .withStatusLabel(firstNameValidationMessage)
                .bind(UserEntity::getRoleEnums, UserEntity::setRoleEnums);
        columnRole.setEditorComponent(rolesBox);

        Button saveBtn = new Button(BTN_SAVE_NAME, e -> editor.save());
        Button cancelBtn = new Button(VaadinIcon.CLOSE.create(), e-> editor.cancel());
        cancelBtn.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);

        HorizontalLayout actions = new HorizontalLayout(saveBtn,
                cancelBtn);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);


        entityGrid.setItems(VaadinSpringDataHelpers.fromPagingRepository(userRepository));


        editor.addSaveListener(e -> {
            userRepository.save(e.getItem());
        });


        add(entityGrid);


    }
}

package com.parking.www.views.user;

import com.parking.www.model.RoleEnum;
import com.parking.www.model.entity.UserEntity;
import com.parking.www.model.service.UserRepository;
import com.parking.www.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;

import java.time.Instant;

@PageTitle("Users list")
@Route(value = "user_list", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UserListView extends VerticalLayout {

    static final String ID = "id";
   static final String USERNAME = "username";
   static final String NAME = "name";
   static final String ROLEENUMS = "roleEnums";
   static final String BTN_EDIT_NAME = "Edit";
   static final String BTN_REMOVE_NAME = "Remove";
   static final String BTN_SAVE_NAME = "Save";
    private final UserRepository userRepository;
    public UserListView(UserRepository userRepository) {
        this.userRepository = userRepository;
        Grid<UserEntity> entityGrid = new Grid<>(UserEntity.class, false);
        Editor<UserEntity> editor = entityGrid.getEditor();

        add(new H1("Users List"));

        Button newUserBtn = new Button("add New");
        add(newUserBtn);

        //  new user
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);

        Dialog dialogNewUser = new Dialog();
        dialogNewUser.setHeaderTitle("New User");
        TextField usernameFieldDialog = new TextField(USERNAME);
        usernameFieldDialog.setRequired(true);

        TextField nameFieldDialog = new TextField(NAME);
        nameFieldDialog.setRequired(true);

        MultiSelectComboBox<RoleEnum> rolesBoxFieldDialog = new MultiSelectComboBox<>("role");
        rolesBoxFieldDialog.setItems(RoleEnum.values());
        rolesBoxFieldDialog.setRequired(true);

        TextField passwordFieldDialog = new TextField("password");
        passwordFieldDialog.setRequired(true);

        contentLayout.add(usernameFieldDialog);
        contentLayout.add(nameFieldDialog);
        contentLayout.add(rolesBoxFieldDialog);
        contentLayout.add(passwordFieldDialog);
        dialogNewUser.add(contentLayout);



        Button saveNewUserBtn = new Button("Save", e -> {
            UserEntity newEntity = new UserEntity();
            newEntity
                    .setUsername(usernameFieldDialog.getValue())
                    .setRoleEnums(rolesBoxFieldDialog.getValue())
                    .setName(nameFieldDialog.getValue())
                    .setCreated(Instant.now())
                    .setModified(Instant.now());

            if(!usernameFieldDialog.getValue().isBlank() &&
                !rolesBoxFieldDialog.getValue().isEmpty() &&
                        !nameFieldDialog.getValue().isBlank() ) {

                userRepository
                        .save(newEntity);

                entityGrid
                        .setItems(userRepository.findAll());

                dialogNewUser.close();
            }


        });

        Button cancelNewUserBtn = new Button("Cancel",e -> dialogNewUser.close());

        cancelNewUserBtn.addThemeVariants(ButtonVariant.LUMO_ICON,ButtonVariant.LUMO_ERROR);

        dialogNewUser.getFooter().add(cancelNewUserBtn,saveNewUserBtn);

       add(dialogNewUser);


//        entityGrid.setColumns(ID,"username","name","roleEnums");

        final Grid.Column<UserEntity> columnId = entityGrid.addColumn(ID).setWidth("10px");
        final Grid.Column<UserEntity> columnUsername = entityGrid.addColumn(USERNAME);
        final Grid.Column<UserEntity> columnName = entityGrid.addColumn(NAME);
        final Grid.Column<UserEntity> columnRole = entityGrid.addColumn(ROLEENUMS).setHeader("Roles")
                .setRenderer(new TextRenderer<>(e -> e.getRoleEnums().toString().replaceAll("[\\[\\]]", "")));


        final Grid.Column<UserEntity>[] removeColumn = new Grid.Column[1];

        Grid.Column<UserEntity> editColumn = entityGrid.addComponentColumn(person -> {
            Button editButton = new Button(BTN_EDIT_NAME);
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                entityGrid.getEditor().editItem(person);

                if (removeColumn[0] != null) {
                    removeColumn[0].setVisible(!removeColumn[0].isVisible());
                }
            });

            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        removeColumn[0] = entityGrid.addComponentColumn(person -> {
            Button removeButton = new Button(BTN_REMOVE_NAME);
            removeButton.addClickListener(e -> {
                userRepository.deleteById(person.getId());
                entityGrid.setItems(userRepository.findAll());
            });
            return removeButton;
        }).setWidth("100px").setFlexGrow(0);





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

        Button saveBtn = new Button(BTN_SAVE_NAME, e -> {
            editor.save();
            removeColumn[0].setVisible(!removeColumn[0].isVisible());
        });
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

        newUserBtn.addClickListener(e -> {

        dialogNewUser.open();
        });


        add(entityGrid);


    }
}

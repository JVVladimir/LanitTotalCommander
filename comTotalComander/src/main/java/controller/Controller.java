package controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import tools.FileInfo;
import tools.FileManager;
import tools.FileType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Controller {

    // Списки для выбора диска
    public ChoiceBox choiceBox1;
    public ChoiceBox choiceBox2;
    // Списки объектов для отображения
    private ObservableList<FileInfo> fileData1 = FXCollections.observableArrayList();
    private ObservableList<FileInfo> fileData2 = FXCollections.observableArrayList();
    // Таблицы с файлами
    public TableView<FileInfo> tableView1;
    public TableView<FileInfo> tableView2;
    // Столбцы таблиц
    public TableColumn<FileInfo, java.lang.String> dateChanged1;
    public TableColumn<FileInfo, java.lang.String> dateCreate1;
    public TableColumn<FileInfo, java.lang.String> dateChanged2;
    public TableColumn<FileInfo, java.lang.String> dateCreate2;
    public TableColumn<FileInfo, java.lang.String> name1;
    public TableColumn<FileInfo, java.lang.String> type1;
    public TableColumn<FileInfo, Long> size1;
    public TableColumn<FileInfo, java.lang.String> name2;
    public TableColumn<FileInfo, java.lang.String> type2;
    public TableColumn<FileInfo, Long> size2;

    public Button btnRef;
    public Button btnBack1;
    public Button btnBack2;

    // private static final java.lang.String DEFAULTPATH = "C://users//VKashitsyn";
    private Path parentPath1;
    private Path parentPath2;
    private static final int FIRSTTREE = 0;
    private static final int SECONDTREE = 1;

    private ArrayList<FileInfo> files1;
    private ArrayList<FileInfo> files2;


    // Инициализируем форму начальными данными
    @FXML
    private void initialize() {
        // Заполнение списков
        ArrayList<String> list = FileManager.getRoots();
        parentPath1 = Paths.get(list.get(0));
        parentPath2 = Paths.get(list.get(0));
        choiceBox1.setValue(list.get(0));
        choiceBox2.setValue(list.get(0));
        choiceBox1.setItems(FXCollections.observableArrayList(list));
        choiceBox2.setItems(FXCollections.observableArrayList(list));
        // Создание слушателей для элементов списков\
        choiceBox1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                parentPath1 = Paths.get((String) choiceBox1.getItems().get(newValue.intValue()));
                resetButtonName();
                showFileSystem(FIRSTTREE);
            }
        });
        choiceBox2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                parentPath2 = Paths.get((String) choiceBox2.getItems().get(newValue.intValue()));
                resetButtonName();
                showFileSystem(SECONDTREE);
            }
        });
        // Заполнение кнопок
        btnBack1.setText(parentPath1.toAbsolutePath().toString());
        btnBack2.setText(parentPath2.toAbsolutePath().toString());
        Image imageBack = new Image(getClass().getClassLoader().getResourceAsStream("back.jpg"));
        Font f = new Font(18);
        btnBack1.setFont(f);
        btnBack2.setFont(f);
        btnBack1.graphicProperty().setValue(new ImageView(imageBack));
        btnBack2.graphicProperty().setValue(new ImageView(imageBack));
        // Переименование кнопок
        resetButtonName();
        // Image imageDir = new Image(getClass().getClassLoader().getResourceAsStream("folder.jpg"));
        // Image imageFile = new Image(getClass().getClassLoader().getResourceAsStream("file.png"));
        // Разрешаем множественный выбор
        tableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Создаём списки файлов для отображения
        showFileSystem(0);
        showFileSystem(1);
        // Создание и заполнение таблиц
        name1.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("name"));
        type1.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("extend"));
        dateCreate1.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("dateCreation"));
        dateChanged1.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("dateMod"));
        size1.setCellValueFactory(new PropertyValueFactory<FileInfo, Long>("size"));
        tableView1.setItems(fileData1);
        name2.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("name"));
        type2.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("extend"));
        dateCreate2.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("dateCreation"));
        dateChanged2.setCellValueFactory(new PropertyValueFactory<FileInfo, java.lang.String>("dateMod"));
        size2.setCellValueFactory(new PropertyValueFactory<FileInfo, Long>("size"));
        tableView2.setItems(fileData2);
    }

    // Обновление деревьев файлов (при изменении ObservableList автоматически перерисовывается таблица)
    public void onRefresh(ActionEvent actionEvent) {
        showFileSystem(0);
        showFileSystem(1);
    }

    // Переход на директорию выше
    private void changeDirectoryUp(Path newPath, int num) {
        if (num == 0) {
            Path p = newPath.getParent();
            if (p == null)
                return;
            else
                parentPath1 = p;
            showFileSystem(0);
            System.out.println(newPath.toString() + "    " + parentPath1);
        } else if (num == 1) {
            Path p = newPath.getParent();
            if (p == null)
                return;
            else
                parentPath2 = p;
            showFileSystem(0);
            System.out.println(newPath.toString() + "    " + parentPath2);
            showFileSystem(1);
        }
    }

    // Переход на директорию ниже
    private void changeDirectoryDown(Path newPath, int num) {
        if (num == 0) {
            parentPath1 = newPath;
            showFileSystem(0);
        } else if (num == 1) {
            parentPath2 = newPath;
            showFileSystem(1);
        }
    }

    /**
     * Функция обновления списка файлов
     */

    public void showFileSystem(int num) {
        Path parentPath = null;
        ArrayList<FileInfo> files;
        FileManager file = new FileManager();
        if (num == FIRSTTREE)
            parentPath = this.parentPath1;
        else if (num == SECONDTREE)
            parentPath = this.parentPath2;
        System.out.println("Show file System: " + parentPath);
        files = file.loadFileTree(parentPath);
        if (num == FIRSTTREE) {
            fileData1.clear();
            fileData1.addAll(files);
            this.files1 = files;
        } else if (num == SECONDTREE) {
            fileData2.clear();
            fileData2.addAll(files);
            this.files2 = files;
        }
        resetButtonName();
        System.out.println("Список обновлён!");
    }

    private String getFileType(ArrayList<FileInfo> file, java.lang.String name) {
        for (int i = 0; i < file.size(); i++) {
            FileInfo info = file.get(i);
            if (info.getName().equals(name))
                return info.getType();
        }
        return null;
    }

    private void eventHandler(MouseEvent mouseEvent, TableView<FileInfo> tableView, ArrayList<FileInfo> files, Path parentPath, int num) {
        ObservableList<FileInfo> list = tableView.getSelectionModel().getSelectedItems();
        if (list == null)
            return;
        if (mouseEvent.getClickCount() > 1 && mouseEvent.getButton() == MouseButton.PRIMARY && list.size() == 1) {
            System.out.println("Double clicked left");
            FileInfo info = list.get(0);
            String p = info.getName();
            String fullPath = parentPath + "\\" + p;
            System.out.println("Переходим к наследнику " + fullPath);
            String type = getFileType(files, p);
            System.out.println(type);
            if (type.equals(FileManager.getStringByType(FileType.DIRECTORY)))
                changeDirectoryDown(Paths.get(fullPath), num);
            else {
                try {
                    Desktop.getDesktop().open(new File(fullPath + info.getExtend()));
                } catch (IOException e) {
                    System.out.println("****Не удалось открыть файл****");
                    e.printStackTrace();
                }
            }
        } else if (mouseEvent.getClickCount() == 1 && mouseEvent.getButton() == MouseButton.SECONDARY) {
            System.out.println("Clicked right");

        }
    }

    // Переименование кнопок
    private void resetButtonName() {
        btnBack1.setText(parentPath1.toAbsolutePath().toString());
        btnBack2.setText(parentPath2.toAbsolutePath().toString());
    }

    // Обработчики кнопок "назад"
    public void onBtnBack1(MouseEvent mouseEvent) {
        String p = btnBack1.getText();
        changeDirectoryUp(Paths.get(p), FIRSTTREE);
        btnBack1.setText(parentPath1.toAbsolutePath().toString());
    }

    public void onBtnBack2(MouseEvent mouseEvent) {
        String p = btnBack2.getText();
        changeDirectoryUp(Paths.get(p), SECONDTREE);
        btnBack2.setText(parentPath2.toAbsolutePath().toString());
    }

    // Обработчики нажатий на элементы таблиц
    public void onClickedTableView1(MouseEvent mouseEvent) {
        eventHandler(mouseEvent, tableView1, files1, parentPath1, FIRSTTREE);
    }

    public void onClickedTableView2(MouseEvent mouseEvent) {
        eventHandler(mouseEvent, tableView2, files2, parentPath2, SECONDTREE);
    }
}

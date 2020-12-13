package com.backEnd;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Класс объекта описывающего маршрут
 */
public class Route {

    /**
     * Поля
     * */
    private final SimpleLongProperty id = new SimpleLongProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty typeOfTransport = new SimpleStringProperty();
    private final SimpleIntegerProperty length = new SimpleIntegerProperty();
    private final SimpleIntegerProperty countOfStops = new SimpleIntegerProperty();

/**
 * Пустой конструктор
 * */
    public Route() {
        id.set(0L);
        name.set("");
        typeOfTransport.set("");
        length.set(0);
        countOfStops.set(0);
    }

    /**
     * Конструктор с параметрами
     * */
    public Route(String name, String typeOfTransport, int length, int countOfStops) {
        this.name.set(name);
        this.typeOfTransport.set(typeOfTransport);
        this.length.set(length);
        this.countOfStops.set(countOfStops);
    }

    /**
     * Геттеры и сеттеры
     * */
    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getTypeOfTransport() {
        return typeOfTransport.get();
    }

    public void setTypeOfTransport(String typeOfTransport) {
        this.typeOfTransport.set(typeOfTransport);
    }

    public SimpleStringProperty typeOfTransportProperty() {
        return typeOfTransport;
    }

    public int getLength() {
        return length.get();
    }

    public void setLength(int length) {
        this.length.set(length);
    }

    public SimpleIntegerProperty lengthProperty() {
        return length;
    }

    public int getCountOfStops() {
        return countOfStops.get();
    }

    public void setCountOfStops(int countOfStops) {
        this.countOfStops.set(countOfStops);
    }

    public SimpleIntegerProperty countOfStopsProperty() {
        return countOfStops;
    }
}
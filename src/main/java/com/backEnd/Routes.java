package com.backEnd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.backEnd.Route;

/**
 * Класс описывающий список маршрутов
 */
public class Routes {

    /**
     * Отслеживаемый список (Аналог <code>LIST</code>)
     */
    private ObservableList<Route> routes;

    private Long count = 1L;

    public Routes() {
        this.routes = FXCollections.observableList(new ArrayList<Route>());
    }

    public Routes(ObservableList<Route> routes) {
        this.routes = FXCollections.observableArrayList(routes);
    }

    public void clear(){
        this.count = 1L;
        this.routes.clear();
    }

    public ObservableList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ObservableList<Route> routes) {
        this.routes = FXCollections.observableArrayList(routes);
    }

    public Route getById(Long id) {
        for (var el : routes) {
            if (el.getId() == id) return el;
        }
        return null;
    }

    public boolean add(Route route) {
        route.setId(count++);
        if (getById(route.getId()) != null) return false;
        return routes.add(route);
    }

    public boolean remove(long id) {
        if (routes == null || getById(id) == null) return false;
        return routes.remove(getById(id));
    }

    public void sortByType() {
        Comparator<Route> comparator = Comparator.comparing(Route::getTypeOfTransport).reversed().thenComparing(Route::getLength).reversed();
        routes.sort(comparator);
    }

    public Routes betweenStops(int min, int max) {
        var routes = new Routes();
        for (var el : this.routes) {
            if (el.getCountOfStops() > min && el.getCountOfStops() < max) routes.add(el);
        }
        return routes;
    }

    public ObservableList<ResultOne> resultOne() {
        List<String> typesList = new ArrayList<>();
        ObservableList<ResultOne> result = FXCollections.observableArrayList(new ArrayList<>());

        for (var route : getRoutes()) {
            boolean flag = false;
            for (var el : typesList) {
                if (route.getTypeOfTransport().equals(el)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) typesList.add(route.getTypeOfTransport());
        }

        for (var el : typesList) {
            var min = Integer.MAX_VALUE;
            var max = Integer.MIN_VALUE;
            for (var route : getRoutes()) {
                if (el.equals(route.getTypeOfTransport())) {
                    if (route.getCountOfStops() > max) max = route.getCountOfStops();
                    if (route.getCountOfStops() < min) min = route.getCountOfStops();
                }
            }
            result.add(new ResultOne(el, min, max));
        }
        return result;
    }

    public int resultTwo(){ return routes.size(); }

    public void removeDownThen(int value) {
        this.getRoutes().removeIf(n -> (n.getLength() < value));
    }

    public Routes filter(String type) {
        var routes = new Routes();
        for (var el : this.getRoutes()) {
            if (el.getTypeOfTransport().toLowerCase().startsWith(type.toLowerCase())) {
                routes.add(el);
            }
        }
        return routes;
    }

}


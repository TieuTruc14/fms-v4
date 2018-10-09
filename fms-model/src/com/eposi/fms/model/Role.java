package com.eposi.fms.model;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = -7726630403554270995L;

    private long      id;
    private Route     route;
	private Rule      rule;
	private Position  position;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

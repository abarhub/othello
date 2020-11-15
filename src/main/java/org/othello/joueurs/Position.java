package org.othello.joueurs;

import org.othello.model.ModelOthello;

import java.awt.geom.Point2D;

/**
 * User: Barret
 * Date: 12 déc. 2009
 * Time: 12:06:38
 */
class Position {
    private Point2D point;
    private ModelOthello model;
    private int score;

    public Position(Point2D point, ModelOthello model) {
        this.point = point;
        this.model = model;
    }

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public ModelOthello getModel() {
        return model;
    }

    public void setModel(ModelOthello model) {
        this.model = model;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toString() {
        String s;
        int x, y;
        if (point == null) {
            x = -1;
            y = -1;
        } else {
            x = (int) point.getX();
            y = (int) point.getY();
        }
        s = "[model=" + model + ",pos=(" + x + "," + y + "),score=" + score + "]";
        return s;
    }
}

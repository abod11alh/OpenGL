package com.company;

import java.util.*;


public class Grid {


    private List<LinkedList<Partical>> cells;

    private int cellscount ;


    public Grid(int cellscount) {
        this.cellscount = cellscount;
        cells=new ArrayList<>();
        for(int i=0;i<cellscount;i++)
        {
            cells.add(new LinkedList<>());
        }
    }

    public int getCellscount() {
        return cellscount;
    }

    public void setCellscount(int cellscount) {
        this.cellscount = cellscount;
    }



    public List<LinkedList<Partical>> getCells() {
        return cells;
    }

    public void setCells(List<LinkedList<Partical>> cells) {
        this.cells = cells;
    }
    public  void AddParticalToCell(int index,Partical partical)
    {
        cells.get(index).add(partical);
    }
    public void DeleteParticalToCell(int index,Partical partical)
    {
        cells.get(index).remove(partical);
    }
}
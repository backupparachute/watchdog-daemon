/*
 *   Copyright (c) Business Integration Technology  All rights reserved. *   http://www.businessintegrationtechnology.com 
 */
package com.kylemiller.watchdogd.util;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * The Class SimplePaginatedList.
 */
public class SimplePaginatedList implements PaginatedList {

    private List list;
    private int pageNumber;
    private String sortCriterion;
    private int fullListSize;
    private int objectsPerPage = 10;
    private String searchId;
    private String sortDirection;
    
    public List getList() {
        return list;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getObjectsPerPage() {
        return objectsPerPage;
    }
    
    public void setObjectsPerPage(int i) {
        this.objectsPerPage = i;
    }

    public int getFullListSize() {
        return fullListSize;
    }

    public String getSortCriterion() {
        return sortCriterion;
    }

    public SortOrderEnum getSortDirection() {
        return SortOrderEnum.fromName(sortDirection);
    }
    
    public void setSortDirection(String dir) {
        this.sortDirection = dir;
    }

    public String getSearchId() {
        return searchId;
    }
    
    public void setSearchId(String s) {
        this.searchId = s;
    }

    public void setList(List list) {
        this.list = list;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }

    public void setFullListSize(int fullListSize) {
        this.fullListSize = fullListSize;
    }
    
    public int calculateStartingIndex() {
        return (getPageNumber() * getObjectsPerPage()) - getObjectsPerPage();
    }

}

package br.com.monitoratec.farol.graphql.model.responses.common;

import org.springframework.data.domain.Page;

public class PaginationInfo {
    private Integer numberOfRecordsByPage;
    private Integer pageNumber;
    private Long totalRecords;

    public PaginationInfo() {}

    public PaginationInfo(Integer numberOfRecordsByPage, Integer pageNumber, Long totalRecords) {
        this.numberOfRecordsByPage = numberOfRecordsByPage;
        this.pageNumber = pageNumber;
        this.totalRecords = totalRecords;
    }

    public PaginationInfo(Page<?> page) {
        this.numberOfRecordsByPage = page.getSize();
        this.pageNumber = page.getNumber();
        this.totalRecords = page.getTotalElements();
    }

    public Integer getNumberOfRecordsByPage() {
        return numberOfRecordsByPage;
    }

    public void setNumberOfRecordsByPage(Integer numberOfRecordsByPage) {
        this.numberOfRecordsByPage = numberOfRecordsByPage;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}

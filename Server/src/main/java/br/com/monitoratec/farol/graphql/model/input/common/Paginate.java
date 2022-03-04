package br.com.monitoratec.farol.graphql.model.input.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Paginate {
    private static final Integer MAX_RECORDS_BY_PAGE = 100;

    private Integer numberOfRecordsByPage;
    private Integer pageNumber;

    public Paginate() {
    }

    public Paginate(Integer numberOfRecordsByPage, Integer pageNumber) {
        this.numberOfRecordsByPage = numberOfRecordsByPage;
        this.pageNumber = pageNumber;
    }

    public static Pageable getPageable(Paginate paginate) {
        return Paginate.getPageable(paginate, null);
    }

    public static Pageable getPageable(Paginate paginate, Sort sort) {
        if (paginate != null) {
            if (paginate.getNumberOfRecordsByPage() > MAX_RECORDS_BY_PAGE) {
                paginate.setNumberOfRecordsByPage(MAX_RECORDS_BY_PAGE);
            }

            if (sort != null) {
                return PageRequest.of(paginate.getPageNumber(), paginate.getNumberOfRecordsByPage(), sort);
            } else {
                return PageRequest.of(paginate.getPageNumber(), paginate.getNumberOfRecordsByPage());
            }
        } else {
            if (sort != null) {
                return PageRequest.of(0, MAX_RECORDS_BY_PAGE, sort);
            } else {
                return PageRequest.of(0, MAX_RECORDS_BY_PAGE);
            }
        }
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
}

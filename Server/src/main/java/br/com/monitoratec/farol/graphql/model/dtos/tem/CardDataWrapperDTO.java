package br.com.monitoratec.farol.graphql.model.dtos.tem;

import java.util.List;

public class CardDataWrapperDTO {
    private List<CardDataDTO> data;
    private Integer updateCount;
    private Integer rowCount;

    public CardDataWrapperDTO(List<CardDataDTO> data, Integer updateCount, Integer rowCount) {
        this.data = data;
        this.updateCount = updateCount;
        this.rowCount = rowCount;
    }

    public List<CardDataDTO> getData() {
        return data;
    }

    public void setData(List<CardDataDTO> data) {
        this.data = data;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}

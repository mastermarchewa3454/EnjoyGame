package com.enjoy.mathero.ui.model.response;

/**
 * Response model for operation status
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class OperationStatusModel {

    private String operationResult;
    private String operationName;

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

}

package com.zihai.activiti.util;

import java.io.InputStream;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

public class HistoryProcessInstanceDiagramCmd implements Command<InputStream> {
    protected String historyProcessInstanceId;
    
    protected String[] executionids;

    public HistoryProcessInstanceDiagramCmd(String proccessid,String... executionids) {
        this.historyProcessInstanceId = proccessid;
        this.executionids = executionids;
    }

    public InputStream execute(CommandContext commandContext) {
        try {
            CustomProcessDiagramGenerator customProcessDiagramGenerator = new CustomProcessDiagramGenerator();

            return customProcessDiagramGenerator
                    .generateDiagram(historyProcessInstanceId,executionids);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

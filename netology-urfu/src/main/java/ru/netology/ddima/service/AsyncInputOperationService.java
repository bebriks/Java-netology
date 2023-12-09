package ru.netology.ddima.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.netology.ddima.configuration.OperationProcessingProperties;
import ru.netology.ddima.domain.Operation;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Queue;


@RequiredArgsConstructor
@Component
public class AsyncInputOperationService {
    public final Queue<Operation> operations = new LinkedList<>();
    public final StatementService statementService;
    public final OperationProcessingProperties properties;


    public boolean addOperation(Operation operation) {
        System.out.println("Operation added for processing" + operation);
        return operations.offer(operation);
    }
    @PostConstruct
    public void startProcessing(){
        Thread t = new Thread() {
            @Override
            public void run() {
                processQueue();
            }
        };
        t.start();
    }
    private void processQueue(){
        while(true){
            Operation operation = operations.poll();
            if(operation == null){
                try{
                    System.out.println("No operations");
                    Thread.sleep(properties.getSleepMilliSeconds());
                } catch(InterruptedException e) {
                    System.out.println(e);
                }
            }
            else {
                System.out.println("Processing operation" + operation);
                processOperation(operation);
            }
        }
    }
    private void processOperation(Operation operation) {statementService.saveOperation(operation);}
    @PostConstruct
    public void init(){
        this.startProcessing();
    }
}

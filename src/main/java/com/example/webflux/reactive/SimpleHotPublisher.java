package com.example.webflux.reactive;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

@Slf4j
public class SimpleHotPublisher implements Flow.Subscriber<Integer> {
    private final ExecutorService publisherExecutor = Executors.newSingleThreadExecutor();
    private final Future<Void> task;
    private List<Integer> numbers;
    private List<SimpleHotSubscription> subscriptions = new ArrayList<>();

    public SimpleHotPublisher(){
        numbers = new ArrayList<>();
        numbers.add(1);
        task = publisherExecutor.submit(()->{
            for( int i =0; Thread.interrupted(); i++){
                numbers.add(i);
                log.info("numbers:{}",numbers);
                subscriptions.forEach(SimpleHotSubscription::wakeup);
                Thread.sleep(100);
            }
            return null;
        });
    }

    public void shutdown(){
        this.task.cancel(true);
        publisherExecutor.shutdown();
    }
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        SimpleHotSubscription simpleHotSubscription = new SimpleHotSubscription();

    }

    @Override
    public void onNext(Integer item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {
        var subscription = new SimpleHotSubscription();

    }

    public static class SimpleHotSubscription implements Flow.Subscription{
        @Override
        public void request(long n) {

        }

        @Override
        public void cancel() {

        }

        public void wakeup(){

        }
    }
}

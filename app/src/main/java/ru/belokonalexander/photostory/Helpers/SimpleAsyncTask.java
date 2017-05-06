package ru.belokonalexander.photostory.Helpers;

import android.os.AsyncTask;

/**
 * враппер для упрощенного испоьзования AsyncTask
 * @param <T>
 */
public class SimpleAsyncTask<T> {

    private AsyncTask<Void,Void,T> backgroundTaskWrapper;

    private InBackground<T> inBackgroundTask;
    private PostExecute<T> postExecute;



    private SimpleAsyncTask(InBackground<T> bt, PostExecute<T> pe) {
        this.inBackgroundTask = bt;
        this.postExecute = pe;

        backgroundTaskWrapper = new AsyncTask<Void, Void, T>() {
            @Override
            protected T doInBackground(Void... params) {


                if(inBackgroundTask!=null)
                    return inBackgroundTask.doInBackground();

                return null;
            }

            @Override
            protected void onPostExecute(T result) {
                super.onPostExecute(result);
                if(postExecute!=null)
                    postExecute.doPostExecute(result);
            }
        };
    }

    /**
     * выполняется или уже выполнено
     * @return
     */
    public boolean isExecuted() {
        return backgroundTaskWrapper.getStatus()!= AsyncTask.Status.PENDING;
    }

    /**
     * выполняется в данный момент
     * @return
     */
    public boolean isRunning(){
        return !backgroundTaskWrapper.isCancelled() && backgroundTaskWrapper.getStatus()== AsyncTask.Status.RUNNING ;
    }

    public void interrupt(){
        backgroundTaskWrapper.cancel(true);
    }

    public static<S> SimpleAsyncTask create(InBackground<S> inBackgroundTask, PostExecute<S> postExecute){
        return new SimpleAsyncTask<S>(inBackgroundTask, postExecute);
    }

    public static<S> SimpleAsyncTask create(InBackground<S> inBackgroundTask){
        return new SimpleAsyncTask<S>(inBackgroundTask, null);
    }

    public static<S> SimpleAsyncTask run(InBackground<S> inBackgroundTask, PostExecute<S> postExecute){
        SimpleAsyncTask s = new SimpleAsyncTask<S>(inBackgroundTask, postExecute);
        s.execute();
        return s;
    }

    public static<S> SimpleAsyncTask createDelayed(PostExecute<S> postExecute, int delay){

        InBackground<S> inBackground = () -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        };

        SimpleAsyncTask s = new SimpleAsyncTask<>(inBackground, postExecute);
        return s;
    }

    public static<S> SimpleAsyncTask run(InBackground<S> inBackgroundTask){
        SimpleAsyncTask s = new SimpleAsyncTask<S>(inBackgroundTask, null);
        s.execute();
        return s;
    }




    public void execute(){
        backgroundTaskWrapper.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public interface InBackground<T>{
        T doInBackground();
    }

    public interface PostExecute<T>{
        void doPostExecute(T result);
    }

    public void setPostExecute(PostExecute<T> postExecute) {
        this.postExecute = postExecute;
    }

    public PostExecute<T> getPostExecute() {
        return postExecute;
    }
}

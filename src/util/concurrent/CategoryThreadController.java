package util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import main.DataController;
import model.SearchField;
import model.SearchHistory;
import util.Constants.Action;
import util.Constants.Category;
import util.Constants.Hotels;
import util.Constants.Travels;
import util.concurrent.TravelsThread;

/* Singleton thread controller. */
public class CategoryThreadController {
    public static final CategoryThreadController INSTANCE = new CategoryThreadController();

    private static final int CORE_POOL_SIZE    = 10;
    private static final int MAXIMUM_POOL_SIZE = 40;
    private static final int KEEP_ALIVE_TIME   = 10;
    private static final int QUEUE_SIZE        = 1000;    
    
    private static Thread tracker;
    
    /* This allows the threads to have a common view of the current search. */
    private static SearchHistory currentSearchHistory;    

    /* Thread pool for all the APIs. */
    private ThreadPoolExecutor threadPool;
    
    private CategoryThreadController() {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(QUEUE_SIZE));
        threadPool.allowCoreThreadTimeOut(true);
        
        /* Thread to keep track if all the threads have completed their tasks. */
        tracker = new Thread() {
            @Override
            public void run() {
                DataController dataController = DataController.INSTANCE;
                
                while (threadPool.getTaskCount() != threadPool.getCompletedTaskCount()) {
                    try {
                        Thread.sleep(500);
                        
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                
                /* If is a new search term. */
                if (dataController.getSearchHistory(getCurrentSearchField()) == null) {
                    dataController.storeSearchHistory(currentSearchHistory);
                }
                
                dataController.setDisplayQueueFinished(true);
            }
        };        
    }
    
    /* Default execute action is SEARCH. */
    public void execute(Category category, SearchField searchField) {
        execute(category, searchField, Action.SEARCH);
    }    
    
    public void execute(Category category, SearchField searchField, Action action) {
        /* Reset search view. */
        currentSearchHistory = new SearchHistory(searchField);

        switch (category) {
            default:
                break;
            
            /* For now, the idea is to do all at one go. */
            case ALL:
                for (Travels travels : Travels.values()) {
                    threadPool.execute(new TravelsThread(travels, action));
                }
                
                for (Hotels hotels : Hotels.values()) {
                    threadPool.execute(new HotelsThread(hotels, action));
                }
                
                break;
                
            case HOTELS:
            case FLIGHTS:
            case SOCIALS:
            case TRAVELS:
                break;
        }

        tracker.start();
    }
    
    public SearchHistory getCurrentSearchHistory() {
        return currentSearchHistory;
    }
    
    public SearchField getCurrentSearchField() {
        return currentSearchHistory.getSearchField();
    }
}

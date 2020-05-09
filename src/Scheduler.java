import java.util.List;

/**
 * Interfact for schedulers
 * @author tim
 * @version 1.0
 *
 */
public interface Scheduler {
    /**
     * Returns the builds
     * @return the builds
     */
    public List<Build> getBuilds();

    /**
     * Adds a build
     * @param build the build
     */
    public void addBuild(Build build);

    /**
     * Runs the builds
     */
    public void run();

    /**
     * Returns the logs
     * @return the logs
     */
    public List<String> getLogs();
}

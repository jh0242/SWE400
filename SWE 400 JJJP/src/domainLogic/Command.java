package domainLogic;
/**
 * Behaviors required of all commands
 * 
 * @author merlin
 *
 */
public interface Command
{

	/**
	 * Do what the command requires
	 */
	public void execute();

	/**
	 * In a real system, this would not exist. However, since we have no real
	 * user interface, our tests will use this to make sure that the command
	 * executed properly. Each command will specify what should be returned
	 * 
	 * @return the net affect of the command.
	 */
	public Object getResult();

}

package domainLogic;

import data_mapper.FriendMapper;
import domain_model.Session;

/**
 * Tells the system to cancel any pending changes
 * 
 * @author merlin
 *
 */
public class CommandToCancelChanges implements Command
{

	/**
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		Session.getInstance().getUnitOfWork().discard();
		Session.getInstance().getPerson().clear();
		FriendMapper.getInstance();
		FriendMapper.removeUserFromHashMap(Session.getInstance().getPerson().getUsername());
	}

	/**
	 * Nothing needs to be returned here (null). The tests will retrieve
	 * anything they want to check by re-finding appropriate records
	 * 
	 * @see Command#getResult()
	 */
	@Override
	public Object getResult()
	{
		return null;
	}

}

package MockedUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.Scanner;

import domainLogic.Command;
import domainLogic.CommandToSelectUser;
import domain_model.Person;

/**
 * Reads a file of instructions, executes them, and verifies the results as it
 * goes along. Any unexpected results are output to the console.
 * 
 * The file that this class parses has a specific format. Each line is one
 * "instruction" which includes:
 * <ol>
 * <li>The name of the command that should be executed. This MUST match the name
 * of a class that implements Command and that command MUST be in a package
 * named domainLogic
 * <li>Any parameters that are required by the command. These must be in the
 * order required by the constructor of that command
 * <li>A semi-colon
 * <li>&lt optional &gt the toString of any expected result from the execution
 * of the command (what the getResults method should return after the command is
 * executed)
 * <ol>
 * 
 * For example, the Create User command has three parameters and the toString of
 * the resulting object should list them:
 * 
 * CreateUserCommand userName pw display; userName:pw:display
 * 
 * Some commands have no parameters, but return results. For example, here is a
 * line that describes a pending incoming friend list instruction where there is
 * one pending friend request to a user named fred
 * 
 * PendingIncomingFriendList; fred
 * 
 * Lines that start with two asterisks will be ignored
 * 
 * @author Merlin
 * 
 */
public class UserThread implements Runnable
{

	private static final String INSERT_USER_ID = "<userID>";
	private Scanner commandReader;
	private int currentUserID;
	private boolean running;

	/**
	 * Checks to see if this thread is currently running
	 * 
	 * @return true if we are still working
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * 
	 * @param fileTitle
	 *            the name of the file with the instructions for this thread
	 * @throws FileNotFoundException
	 *             if the file is not in the workspace or can't be founed
	 */
	public UserThread(String fileTitle) throws FileNotFoundException
	{
		commandReader = new Scanner(new File(fileTitle));
	}

	/**
	 * Gets the class for a given command
	 * 
	 * @param command
	 *            the command we are working on
	 * @return the class that represents the command
	 */
	protected Class<? extends Command> getCommandClass(String command)
	{
		try
		{
			return (Class<? extends Command>) Class.forName(
					"domainLogic." + command).asSubclass(Command.class);
		} catch (ClassNotFoundException e)
		{
			System.out.println("Unrecognized command: " + command);
		}
		return null;
	}

	/**
	 * Build the complete command object that encodes the action in a given
	 * command description
	 * 
	 * @param commandDescription
	 *            the description of the command we are supposed to execute
	 * @return a Command object encoding the command description
	 */
	protected Command buildCommand(String commandDescription)
	{
		String[] instructionTokens = commandDescription.split(" ");
		Class<? extends Command> commandClass = getCommandClass(instructionTokens[0]);
		Constructor<?>[] constructors = commandClass.getConstructors();
		Class<?>[] parameters = constructors[0].getParameterTypes();
		Command result = null;
		try
		{
			if (parameters.length == 0)
			{
				result = (Command) constructors[0].newInstance();
			} else if (parameters.length == 1)
			{
				if (instructionTokens[1].equals(INSERT_USER_ID))
				{
					result = (Command) constructors[0]
							.newInstance(currentUserID);
				} else
				{
					result = (Command) constructors[0].newInstance(Integer
							.parseInt(instructionTokens[1]));
				}
			} else if (parameters.length == 2)
			{
				if (parameters[0].equals(String.class))
				{
					result = (Command) constructors[0].newInstance(
							instructionTokens[1], instructionTokens[2]);
				} else
				{
					if (instructionTokens[1].equals(INSERT_USER_ID))
					{
						result = (Command) constructors[0].newInstance(
								currentUserID, instructionTokens[2]);
					} else
					{
						result = (Command) constructors[0].newInstance(
								Integer.parseInt(instructionTokens[1]),
								instructionTokens[2]);
					}
				}
			} else if (parameters.length == 3)
			{
				result = (Command) constructors[0].newInstance(
						instructionTokens[1], instructionTokens[2],
						instructionTokens[3]);
			}
		} catch (Exception e)
		{
			System.out.println(Thread.currentThread().getName()
					+ " couldn't create a command for " + commandDescription);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Splits a given instruction into its command description and its expected
	 * results. Trims blanks where appropriate.
	 * 
	 * @param instruction
	 *            the instruction we are processing
	 * @return an array of strings - position zero is always the command
	 *         description and position 1 is the expected results
	 */
	protected String[] splitInstruction(String instruction)
	{
		String[] partial = instruction.split(";");
		String[] results = new String[partial.length];
		for (int i = 0; i < partial.length; i++)
		{
			results[i] = partial[i].trim();
		}
		return results;
	}

	/**
	 * Parse and execute a given instruction.
	 * 
	 * @param instruction
	 *            A string describing the instruction as specified in the
	 *            javadocs for this class
	 * @return true if it behaved as expected and false otherwise
	 */
	protected boolean executeInstruction(String instruction)
	{
		String[] parts = splitInstruction(instruction);
		Command cmd = buildCommand(parts[0]);
		cmd.execute();
		if (cmd instanceof CommandToSelectUser)
		{
			Person selectedUser = (Person)cmd.getResult();
			currentUserID = selectedUser.getID();
		}
		if (parts.length == 2)
		{
			String result = (String) cmd.getResult();
			if (result == null)
			{
				return false;
			}
			return (result.equals(parts[1]));
		}
		return true;
	}

	/**
	 * process the entire file unless an instruction fails
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		this.running = true;
		String input = getNextCommandLine();
		boolean allIsWell = true;
		while (allIsWell && input != null)
		{
			System.out.println(input);
			if (!executeInstruction(input))
			{
				allIsWell = false;
				System.out.println(Thread.currentThread().getName()
						+ " failed when executing this instruction: " + input);
				input = getNextCommandLine();
			} else
			{

				input = getNextCommandLine();

			}
		}
		this.running = false;

	}

	private String getNextCommandLine()
	{
		String input = null;
		if (commandReader.hasNextLine())
		{
			input = commandReader.nextLine();
		}
		while ((input != null) && input.startsWith("**"))
		{
			if (commandReader.hasNextLine())
			{
				input = commandReader.nextLine();
			} else
			{
				input = null;
			}
		}
		return input;
	}

	/**
	 * For testing purposes, force this thread to think it is playing with a
	 * specific userID
	 * 
	 * @param currentUserID
	 *            the user id we should use
	 */
	public void setCurrentUserID(int currentUserID)
	{
		this.currentUserID = currentUserID;
	}

}

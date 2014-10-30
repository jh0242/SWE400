package MockedUI;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates a series of threads for a series of instruction files and runs them all in parallel
 * @author Merlin
 *
 */
public class MultiThreadRunner
{
	/**
	 * 
	 * @param args unused
	 * @throws FileNotFoundException if one of the files the user enters cannot be found
	 * @throws InterruptedException if we interrupt it
	 */
	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException
	{
		ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<UserThread> uThreads = new ArrayList<UserThread>();
		Scanner inputScanner = new Scanner(System.in);
		String input = inputScanner.nextLine();
		String[] fileTitles = input.split(",");
		for(String title:fileTitles)
		{
			System.out.println("Creating Thread for " + title.trim());
			UserThread target = new UserThread(title.trim());
			uThreads.add(target);
			Thread t = new Thread(target);
			threads.add(t);
		}
		inputScanner.close();
		
		for (Thread t : threads)
		{
			t.start();
		}
		for (UserThread t : uThreads)
		{
			while (t.isRunning())
			{
				System.out.println("Still waiting...");
				Thread.sleep(100);  
			}
		}

		System.out.println("Finished!");
	}
}

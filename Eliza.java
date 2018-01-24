import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Eliza {

	static HashMap<String, String> reflections;
	static LinkedHashMap<String, ArrayList<String>> psychobabble;
	static ArrayList<String> generic;
	static HashMap<String, ArrayList<String>> memoryResponses;
	static HashMap<String, String> categories;
	static Stack<String> memory;
	static ArrayList<String> usedMemory;

	//Read user input and calls analyze to output response
	public static void main(String[] args){
		fillPsychobabble();
		fillReflection();
		fillGeneric();
		fillMemoryResponses();
		categorize();
		memory = new Stack<String>();
		usedMemory = new ArrayList<String>();
		
		//Read input from console
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("Hello. How are you feeling today?");;

		while(true){
			String input = scanner.nextLine();

			//Check if the program should stop
			if ("quit".equals(input)){
				System.out.println("Thank you for talking with me. Good bye!");
				break;
			}
			
			//Output response
			System.out.println(analyze(input));
		}
	}

	// Reflections
	public static void fillReflection(){
		reflections = new HashMap<String, String>();
		reflections.put("am", "are");
		reflections.put("was", "were");
		reflections.put("i", "you");
		reflections.put("i'd", "you would");
		reflections.put("i've", "you have");
		reflections.put("i'll", "you will");
		reflections.put("my", "your");
		reflections.put("are", "am");
		reflections.put("you've", "I have");
		reflections.put("you'll", "I will");
		reflections.put("your", "my");
		reflections.put("yours", "mine");
		reflections.put("you", "me");
		reflections.put("me", "you");
		reflections.put("you're", "I'm");
		reflections.put("i'm","you're");
	}

	//Match a key words to a response
	@SuppressWarnings("serial")
	public static void fillPsychobabble(){
		psychobabble = new LinkedHashMap<String, ArrayList<String>>();

		psychobabble.put("I need", new ArrayList<String>(){{ 
			add("Why do you need$replace$?"); 
			add("Would it really help you to get$replace$?"); 
			add("Are you sure you need$replace$?");
		}});
		psychobabble.put("Why don\'t you", new ArrayList<String>(){{ 
			add("Do you really think I don't$replace$?");
			add("Perhaps eventually I will$replace$.");
			add("Do you really want me to$replace$?");
		}});
		psychobabble.put("Why can\'t I", new ArrayList<String>(){{ 
			add("Do you think you should be able to$replace$?");
			add("If you could$replace$ , what would you do?");
			add("I don't know -- why can't you$replace$?");
			add("Have you really tried?");
		}});
		psychobabble.put("I can\'t", new ArrayList<String>(){{ 
			add("How do you know you can't$replace$?");
			add("Perhaps you could$replace$ if you tried.");
			add("What would it take for you to$replace$?");
		}});
		psychobabble.put("I am", new ArrayList<String>(){{ 
			add("Did you come to me because you are$replace$?");
			add("How long have you been$replace$?");
		}});
		psychobabble.put("I\'m", new ArrayList<String>(){{ 
			add("How does being$replace$ make you feel?");
			add("Do you enjoy being$replace$?");
			add("Why do you tell me you're$replace$?");
			add("Why do you think you're$replace$?");
		}});
		psychobabble.put("Are you", new ArrayList<String>(){{ 
			add("What does it matter whether I am$replace$?");
			add("Would you prefer it if I were not$replace$?");
			add("Perhaps you believe I am$replace$.");
			add("I may be$replace$ -- what do you thing?");
		}});
		psychobabble.put("What", new ArrayList<String>(){{ 
			add("Why do you ask?");
			add("How would an answer to that help you?");
			add("What do you think?");
		}});
		psychobabble.put("How", new ArrayList<String>(){{ 
			add("How do you suppose?");
			add("Perhaps you can answer your own question.");
			add("What is it you're really asking?");
		}});
		psychobabble.put("Because", new ArrayList<String>(){{ 
			add("Is that really a reason?");
			add("What other reasons come to mind?");
			add("Does that reason apply to anything else?");
		}});
		psychobabble.put("(.*)sorry", new ArrayList<String>(){{ 
			add("There are many tiems when no apology is needed.");
			add("That feelings do you have when you apologize?");
		}});
		psychobabble.put("Hello", new ArrayList<String>(){{ 
			add("Hello...I'm glad you could drop by today. What would you like to tell me?");
			add("Hi there...how are you today?");
		}});
		psychobabble.put("I think", new ArrayList<String>(){{ 
			add("Do you doubt$replace$?");
			add("Do you really thing so?");
			add("But you're not sure$replace$?");
		}});
		psychobabble.put("(.*) friend", new ArrayList<String>(){{ 
			add("Tell me more about your friends.");
			add("When you think of a friend, what comes to mind?");
			add("Why don't you tellme about a childhood friend?");
		}});
		psychobabble.put("Yes", new ArrayList<String>(){{ 
			add("You seem quite sure.");
			add("OK, but can you elaborate a bit?");
		}});
		psychobabble.put("(.*)computer", new ArrayList<String>(){{ 
			add("How do computers make you feel?");
			add("Do you feel threatened by computers?");
		}});
		psychobabble.put("Is it", new ArrayList<String>(){{ 
			add("Do you thik it is$replace$?");
			add("Perhaos it's$replace$ -- what do you think?");
			add("If it were$replace$, what would you do?");
			add("It could well be that$replace$.");
		}});

		psychobabble.put("It is", new ArrayList<String>(){{ 
			add("You seem very certain.");
			add("If I told you that it probably isn't$replace$, what would you feel?");
		}});

		psychobabble.put("Can you", new ArrayList<String>(){{ 
			add("What makes you think I can't$replace$?");
			add("If I could$replace$, then what?");
			add("Why do you ask if I can$replace$?");
		}});
		psychobabble.put("Can I", new ArrayList<String>(){{ 
			add("Perhaps you don't want to$replace$.");
			add("Do you want to be able to$replace$?");
			add("I you could$replace$, would you?");
		}});
		psychobabble.put("You are", new ArrayList<String>(){{ 
			add("Why do you think I am$replace$");
			add("Does it please you to think that I'm$replace$?");
			add("Perhaps you would like me to be$replace$.");
			add("Perhaps you're really talking about yourself?");
		}});
		psychobabble.put("You\'re", new ArrayList<String>(){{ 
			add("Why do you say I am$replace$?");
			add("Why do you think I am$replace$?");
			add("Are we talking about you, or me?");
		}});
		psychobabble.put("I don\'t", new ArrayList<String>(){{ 
			add("Don't you really$replace$?");
			add("Why don't you$replace$?");;
		}});
		psychobabble.put("I feel", new ArrayList<String>(){{ 
			add("Good, tell me more about these feelings.");
			add("When do you usually feel$replace$?");
			add("Do you often feel$replace$?");
			add("When you feel$replace$, what do you do?");
		}});
		psychobabble.put("I have", new ArrayList<String>(){{ 
			add("Why do you tell me that you've$replace$?");
			add("Have you really$replace$?");
			add("Now that you have$replace$, what will you do next?");
		}});
		psychobabble.put("I would", new ArrayList<String>(){{ 
			add("Could you explain why you would$replace$?");
			add("Why would you$replace$?");
			add("Who else knows that you would$replace$?");
		}});
		psychobabble.put("Is there", new ArrayList<String>(){{ 
			add("Do you think there is$replace$?");
			add("It's likely that there is$replace$.");
			add("Would you like there to be$replace$?");
		}});
		psychobabble.put("My", new ArrayList<String>(){{ 
			add("I see, your$replace$");
			add("Why do you say that your$replace$");
			add("When your$replace$, how do you feel");
		}});
		psychobabble.put("You", new ArrayList<String>(){{ 
			add("We should be discussing you not me.");
			add("Why do you say that about me?");
			add("Whe do you care whether I$replace$?");
		}});
		psychobabble.put("I want", new ArrayList<String>(){{ 
			add("What would it mean to you if you got$replace$?");
			add("Why do you want$replace$?");
			add("What would you do if you got$replace$?");
			add("If you got$replace$,then what would you do?");
		}});
		psychobabble.put("(,*)[Mm]y mother", new ArrayList<String>(){{ 
			add("How do you feel about your mother?");
			add("Good family relations are important.");
			add("How does this relate to your feelings today?");
			add("Tell me more about your mother.");
			add("How was your relationship with your mother like?");
		}});
		psychobabble.put("(,*)[Mm]y father", new ArrayList<String>(){{ 
			add("How do you feel about your father?");
			add("Do you have trouble showing affection with your family?");
			add("Does your relationship with your father relate to your feelings today?");
			add("Tell me more about your father.");
			add("How did your father make you feel?");
		}});
		psychobabble.put("(.*) child", new ArrayList<String>(){{ 
			add("Can you tell me more about your childhood?");
			add("And how do you think your childhood experiences relate to your feelings today?");
			add("What is your favourite childhood memory?");
		}});
		psychobabble.put("(.*)\\?", new ArrayList<String>(){{ 
			add("Why do you ask that?");
			add("Please consider whether you can answer your own question.");
			add("Why don't you tell me?");
		}});
		psychobabble.put("((.*)same)|((.*)alike)", new ArrayList<String>(){{ 
			add("In what way?");
			add("What resemblance do you see?");
			add("Can you give me an example?");
		}});
		psychobabble.put("(.*)something", new ArrayList<String>(){{ 
			add("Can you give me a specific example?");
			add("Can you be more specific?");
		}});
		psychobabble.put("((.*)everyone)|((.*)everybody)|((.*)nobody)|((.*)no one)", new ArrayList<String>(){{ 
			add("Are you thinking of someone in particular?");
			add("Who in particular are you thinking of?");
		}});
		psychobabble.put("((.*)always)|((.*)never)", new ArrayList<String>(){{ 
			add("Are you thinking of a particular time?");
			add("Can you give me an example?");
		}});

	}

	//List of generic responses
	@SuppressWarnings("serial")
	public static void fillGeneric(){
		generic = new ArrayList<String>(){{ 
			add("Please tell me more."); 
			add("Can you elaborate on that?");
			add("I see.");
			add("Very Interesting.");
			add("I see. And what does that tell you?");
			add("How does that make you feel?");
			add("How do you feel when you say that?");
			add("Why do you say that?");
			add("What makes you say that?");
		}};
	}
	
	//Link key memory elements with a category
	public static void categorize(){
		categories = new HashMap<String, String>();
		//Relationship
		categories.put("mother", "relationship");
		categories.put("father", "relationship"); 
		categories.put("sister", "relationship");
		categories.put("brother", "relationship");
		categories.put("grandmother", "relationship");
		categories.put("grandfather", "relationship");
		categories.put("parents", "relationship");
		categories.put("siblings", "relationship");
		categories.put("grandparents", "relationship");
		categories.put("boyfriend", "relationship");
		categories.put("girlfriend", "relationship");
		categories.put("wife", "relationship");
		categories.put("husband", "relationship");
		//Pet
		categories.put("cat", "pet");
		categories.put("dog", "pet");
		categories.put("rabbit", "pet");
		categories.put("turtle", "pet");
		categories.put("fish", "pet");
		categories.put("hamster", "pet");
		categories.put("guinea pig", "pet");
		categories.put("bird", "pet");
		categories.put("gerbil", "pet");
		categories.put("hedgehog", "pet");
		categories.put("pig", "pet");
		categories.put("mouse", "pet");;	
	}
	
	//Match categories of key elements in memory with a response
	@SuppressWarnings("serial")
	public static void fillMemoryResponses(){
		memoryResponses = new HashMap<String, ArrayList<String>>();
		memoryResponses.put("relationship", new ArrayList<String>(){{
			add("Does this relate to your $replace$?");
			add("Do you think this has anything to do with your $replace$?");
			add("I see. Is this because of your $replace$?");
			add("Do you think your relationship with your $replace$ has affected you?");
		}});
		memoryResponses.put("pet", new ArrayList<String>(){{
			add("You mentioned a pet earlier. When did you first own your $replace$?");
			add("I see. Let's talk about your $replace$. How does your $replace$ make you feel?");
			add("Intersting. Let's switch topics a bit. Can you tell me more about your $replace$?");
		}});
	}

	
	//Reflect a fragment of the user input back to the user
	public static String reflect(String fragment){
		//Parse fragment, and replace lower case tokens in reflections with its values
		String[] tokens = fragment.split(" ");
		for(int i = 0; i < tokens.length; i++){
			if(reflections.containsKey(tokens[i].toLowerCase())){
				tokens[i] = reflections.get(tokens[i]);
			}
		}
		//Reconstruct and return the reflected fragment
		String reflectedFragment = String.join(" ", tokens);
		return reflectedFragment;
	}

	
	//Find match pattern (substring) in dictionary
	public static String analyze(String statement){
		String lastResponse = "";
		Random rand = new Random();
		int i;

		//If user enters more than one sentence, split sentences and find matches for each.
		String[] sentences = statement.split("\\.|\\!|,");
		for(String sentence : sentences){
			//Find a match in psychobabble and extract an appropriate response
			for(Map.Entry<String, ArrayList<String>> entry : psychobabble.entrySet()){
				Pattern r = Pattern.compile(entry.getKey() + "(,*)");
				Matcher m = r.matcher(sentence);
				String response;
				String remaining;

				//If a match is found, select a potential response
				if(m.find()){
					ArrayList<String> a = entry.getValue();
					//Pick a random response from available responses
					i = rand.nextInt(a.size());
					response = a.get(i);

					//If necessary, finish response by reflecting remainder of user's answer
					remaining = sentence.substring(m.end());
					remaining = reflect(remaining.replaceAll("[.!?]*$", ""));
					response = response.replace("$replace$", remaining);

					//Keep track of the last matched response
					lastResponse = response;
				}
			}
		}

		//If no matches, look from memory or generic response
		if(lastResponse.equals("")){
			lastResponse = useMemory();
			if(lastResponse.equals("")){
				i = rand.nextInt(generic.size());
				lastResponse = generic.get(i);
			}
		}
		
		//Check for key words to add to memory
		addToMemory(statement);

		//Return response to main
		return lastResponse;
	}

	
	//Check for key words and add to memory
	public static void addToMemory(String statement){
		//Check for key words and add to memory
		for(Entry<String, String> entry : categories.entrySet()){
			Pattern r = Pattern.compile("(,*)[Mm]y " + entry.getKey() + "(,*)");
			Matcher m = r.matcher(statement);
			if(m.find()){
				memory.push(entry.getKey());
			}
		}
	}
	
	
	//Ask about the most recent key word that hasn't been asked before
	public static String useMemory(){
		String response = "";
		String key;
		//Get most recent key word in memory
		if(memory.empty())
			return response;
		key = memory.pop();
		
		//Return an appropriate response
		while(true){
			//Check if key word has been asked before
			if(usedMemory.contains(key)){
				if(memory.empty()){
					return response;
				} else {
					key = memory.pop();
				}
			} else{
				//If key not asked before, check category and return an appropriate response from memoryResponses
				usedMemory.add(key);
				
				ArrayList<String> candidateReponses = memoryResponses.get(categories.get(key));
				Random rand = new Random();
				int i = rand.nextInt(candidateReponses.size());
				response = candidateReponses.get(i).replace("$replace$", key);
				
				return response;
			}
		}
	}
}

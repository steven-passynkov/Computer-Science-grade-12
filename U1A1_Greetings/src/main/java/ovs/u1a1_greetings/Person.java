package ovs.u1a1_greetings;

/**
 * File Name:	Person 
 * Programmer:	Steven Passynkov 
 * Date:	2 Jan 2024
 * Description:	Record to  represent a person to show in Greeting frame
 *
 */
public record Person(String name, int age, 
        String favoriteMusic, String favoriteShow, 
        String favoriteColor, String gitHub,
        String[] techs) {
}

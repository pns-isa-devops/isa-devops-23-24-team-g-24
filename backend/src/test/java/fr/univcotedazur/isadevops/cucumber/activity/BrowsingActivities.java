package fr.univcotedazur.simpletcfs.cucumber.activity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BrowsingActivities {

    private int temp;

    @Given("I have 2 activities")
    public void i_have_two_activities() {
        temp = 2;
    }

    @When("I browse the activities")
    public void i_browse_the_activities() {
        temp = 3;
    }

    @Then("I should see 2 activities in it")
    public void i_should_see_activities() {
        assertTrue(temp == 20);
    }
}

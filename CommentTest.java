import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Comment.
 */
public class CommentTest {
    private Comment comment;

    // Set up the test fixture before each test case
    @Before
    public void setUp() {
        comment = new Comment("Alice", "Great product!", 5); // Initialize with a sample comment
    }

    // Tear down the test fixture after each test case
    @After
    public void tearDown() {
        comment = null;
    }

    // Test that the author and rating are correctly stored
    @Test
    public void testInitialization() {
        assertEquals("Alice", comment.getAuthor());
        assertEquals(5, comment.getRating());
    }

    // Test that the upvote method works as expected
    @Test
    public void testUpvote() {
        assertEquals(0, comment.getVoteCount());  // Initially, vote count should be 0
        
        comment.upvote();
        assertEquals(1, comment.getVoteCount());  // After one upvote, the vote count should be 1

        comment.upvote();
        assertEquals(2, comment.getVoteCount());  // After another upvote, the vote count should be 2
    }

    // Test that the downvote method works as expected
    @Test
    public void testDownvote() {
        assertEquals(0, comment.getVoteCount());  // Initially, vote count should be 0
        
        comment.downvote();
        assertEquals(-1, comment.getVoteCount());  // After one downvote, the vote count should be -1

        comment.downvote();
        assertEquals(-2, comment.getVoteCount());  // After another downvote, the vote count should be -2
    }

    // Test that upvotes and downvotes work together (combination of both)
    @Test
    public void testUpvoteDownvoteCombination() {
        assertEquals(0, comment.getVoteCount());  // Initially, vote count should be 0

        comment.upvote();  // One upvote
        comment.upvote();  // Another upvote
        comment.downvote();  // One downvote
        
        assertEquals(1, comment.getVoteCount());  // After two upvotes and one downvote, the vote count should be 1
    }

    // Test the boundary values for rating (rating 0 and rating 6 are invalid)
    @Test
    public void testInvalidRatings() {
        // Testing rating 0
        Comment commentWithZeroRating = new Comment("Bob", "Poor product.", 0);
        assertEquals(0, commentWithZeroRating.getRating());  // Rating 0 should be accepted but handled as invalid if necessary

        // Testing rating 6
        Comment commentWithSixRating = new Comment("Carol", "Not recommended.", 6);
        assertEquals(6, commentWithSixRating.getRating());  // Rating 6 should be rejected or handled
    }

    // Test that a comment can be upvoted multiple times and the vote count increases correctly
    @Test
    public void testUpvoteMultipleTimes() {
        comment.upvote();
        comment.upvote();
        comment.upvote();  // Upvote three times

        assertEquals(3, comment.getVoteCount());  // Vote count should be 3
    }

    // Test that a comment can be downvoted multiple times and the vote count decreases correctly
    @Test
    public void testDownvoteMultipleTimes() {
        comment.downvote();
        comment.downvote();  // Downvote two times

        assertEquals(-2, comment.getVoteCount());  // Vote count should be -2
    }

    // Test the case where the rating is below the valid range
    @Test
    public void testInvalidLowRating() {
        Comment invalidComment = new Comment("John", "Terrible!", 0);
        assertFalse(invalidComment.getRating() >= 1 && invalidComment.getRating() <= 5);  // Rating below 1 should be invalid
    }

    // Test the case where the rating is above the valid range
    @Test
    public void testInvalidHighRating() {
        Comment invalidComment = new Comment("Sarah", "Amazing!", 6);
        assertFalse(invalidComment.getRating() >= 1 && invalidComment.getRating() <= 5);  // Rating above 5 should be invalid
    }
}


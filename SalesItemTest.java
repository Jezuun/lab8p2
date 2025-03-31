import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SalesItemTest {
    private SalesItem salesItem;

    // Set up the test fixture
    @Before
    public void setUp() {
        salesItem = new SalesItem("Brain surgery for Dummies", 21998); // Price in cents
    }

    // Tear down the test fixture
    @After
    public void tearDown() {
        salesItem = null;
    }

    // Test that a comment can be added, and that the comment count is correct
    @Test
    public void testAddComment() {
        assertTrue(salesItem.addComment("James Duckling", "This book is great. I perform brain surgery every week now.", 4));
        assertEquals(1, salesItem.getNumberOfComments());
    }

    // Test that a comment using an illegal rating value is rejected
    @Test
    public void testIllegalRating() {
        assertFalse(salesItem.addComment("Joshua Black", "Not worth the money. The font is too small.", -5));
        assertFalse(salesItem.addComment("John Doe", "I like the content but not the font size.", 6));
    }

    // Test that a comment from the same author is not added twice
    @Test
    public void testDuplicateCommentByAuthor() {
        salesItem.addComment("John Doe", "Great product, I recommend it!", 5);
        assertFalse(salesItem.addComment("John Doe", "Second comment from same author.", 3));
    }

    // Test that a sales item is correctly initialised (name and price)
    @Test
    public void testInit() {
        assertEquals("Brain surgery for Dummies", salesItem.getName());
        assertEquals(21998, salesItem.getPrice());
    }

    // Test that the findMostHelpfulComment method works as expected
    @Test
    public void testFindMostHelpfulComment() {
        salesItem.addComment("Alice", "Great product!", 5);
        salesItem.addComment("Bob", "Not bad.", 4);
        salesItem.upvoteComment(0);
        salesItem.upvoteComment(0);  // Alice's comment should have the highest vote count
        
        Comment mostHelpful = salesItem.findMostHelpfulComment();
        assertNotNull(mostHelpful);
        assertEquals("Alice", mostHelpful.getAuthor());
        assertEquals(5, mostHelpful.getRating());
    }

    // Test that upvoting and downvoting works as expected
    @Test
    public void testUpvoteAndDownvote() {
        salesItem.addComment("Charlie", "Average item.", 3);
        
        salesItem.upvoteComment(0);
        assertEquals(1, salesItem.findMostHelpfulComment().getVoteCount());
        
        salesItem.downvoteComment(0);
        assertEquals(0, salesItem.findMostHelpfulComment().getVoteCount());
        
        salesItem.downvoteComment(0);
        assertEquals(-1, salesItem.findMostHelpfulComment().getVoteCount());
    }

    // Test invalid comment removal (invalid index)
    @Test
    public void testInvalidCommentRemoval() {
        salesItem.addComment("Alice", "Great product!", 5);
        salesItem.removeComment(10); // Invalid index
        assertEquals(1, salesItem.getNumberOfComments());
        
        salesItem.removeComment(-1); // Invalid index
        assertEquals(1, salesItem.getNumberOfComments());
    }

    // Test removal of a valid comment
    @Test
    public void testValidCommentRemoval() {
        salesItem.addComment("Alice", "Great product!", 5);
        salesItem.addComment("Bob", "Not bad.", 4);
        
        salesItem.removeComment(0);  // Remove first comment
        assertEquals(1, salesItem.getNumberOfComments());
        
        Comment remainingComment = salesItem.findMostHelpfulComment();
        assertEquals("Bob", remainingComment.getAuthor());
        assertEquals(4, remainingComment.getRating());
    }

    // Test edge case for findMostHelpfulComment with no comments
    @Test
    public void testFindMostHelpfulCommentWithNoComments() {
        assertNull(salesItem.findMostHelpfulComment());  // No comments should return null
    }
    
    // Test that addComment returns false when a comment from the same author already exists
    @Test
    public void testAddCommentDuplicateAuthor() {
        salesItem.addComment("Alice", "Great product!", 5);
        assertFalse(salesItem.addComment("Alice", "Another comment from Alice", 4));  // Should return false
    }

    // Test boundary cases for rating (0 and 6)
    @Test
    public void testInvalidRatingBoundaries() {
        assertFalse(salesItem.addComment("John", "This is a great product", 0));  // Rating 0 is invalid
        assertFalse(salesItem.addComment("Jane", "I wouldn't recommend this.", 6));  // Rating 6 is invalid
    }

    // Test that a comment can be upvoted multiple times and returns correct result
    @Test
    public void testUpvoteMultipleTimes() {
        salesItem.addComment("Tom", "Useful product.", 5);
        
        salesItem.upvoteComment(0);
        salesItem.upvoteComment(0);
        salesItem.upvoteComment(0);
        
        assertEquals(3, salesItem.findMostHelpfulComment().getVoteCount());  // Vote count should be 3
    }
    
    // Test that downvote decreases vote count correctly
    @Test
    public void testDownvoteDecreasesVoteCount() {
        salesItem.addComment("Mike", "Not great", 3);
        
        salesItem.downvoteComment(0);
        salesItem.downvoteComment(0);
        
        assertEquals(-2, salesItem.findMostHelpfulComment().getVoteCount());  // Vote count should be -2
    }
}







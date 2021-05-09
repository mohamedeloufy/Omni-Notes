package it.feio.android.omninotes;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class checkList {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction viewInteraction = onView(
                allOf(withId(R.id.fab_expand_menu_button),
                        childAtPosition(
                                allOf(withId(R.id.fab),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        viewInteraction.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_checklist),
                        childAtPosition(
                                allOf(withId(R.id.fab),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.detail_title),
                        childAtPosition(
                                allOf(withId(R.id.title_wrapper),
                                        childAtPosition(
                                                withId(R.id.detail_tile_card),
                                                0)),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("Alarm"), closeSoftKeyboard());

        ViewInteraction editTextMultiLineNoEnter = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("it.feio.android.checklistview.models.CheckListViewItem")),
                                0),
                        2),
                        isDisplayed()));
        editTextMultiLineNoEnter.perform(replaceText("Firstday"), closeSoftKeyboard());

        ViewInteraction editTextMultiLineNoEnter2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("it.feio.android.checklistview.models.CheckListViewItem")),
                                0),
                        2),
                        isDisplayed()));
        editTextMultiLineNoEnter2.perform(replaceText("Secondday"), closeSoftKeyboard());

        ViewInteraction editTextMultiLineNoEnter3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("it.feio.android.checklistview.models.CheckListViewItem")),
                                0),
                        2),
                        isDisplayed()));
        editTextMultiLineNoEnter3.perform(replaceText("Thirdd"), closeSoftKeyboard());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.reminder_layout),
                        childAtPosition(
                                allOf(withId(R.id.fragment_detail_content),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2)));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.buttonPositive), withText("Ok"),
                        childAtPosition(
                                allOf(withId(R.id.button_layout),
                                        childAtPosition(
                                                withId(R.id.llMainContentHolder),
                                                2)),
                                5),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.note_content), withText("◻ Firstday\n◻ Secondday…"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_layout),
                                        1),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("◻ Firstday ◻ Secondday…")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

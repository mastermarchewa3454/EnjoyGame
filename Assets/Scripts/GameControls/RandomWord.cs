using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

/// <summary>
/// Gives a random difficulty to the item
/// </summary>
public class RandomWord : MonoBehaviour
{
    private Text changingText;

    /// <summary>
    /// Gets the Text component of item
    /// </summary>
    void Start()
    {
        changingText = gameObject.transform.GetChild(0).gameObject.GetComponent<Text>();
        TextChange();
    }

    /// <summary>
    /// Selects a random difficulty: Easy, Medium, Hard
    /// </summary>
    /// <returns>Difficulty</returns>
    private string PickRandomDifficulty()
    {
        //get number of correct answered questions
        //int easy = 
        //int medium = 
        //int hard =
        //int total = easy + medium+  hard;
        //if (total<3){
        string[] difficulty = new string[] { "Easy", "Medium", "Hard" };
        string randomdifficulty = difficulty[Random.Range(0, difficulty.Length)];
        return randomdifficulty;
        //}
        //else{
        //  easypercent = easy/total*100;
        //  mediumpercent = medium/total*100;
        //  hardpercent = hard/total*100;
        //  if(easypercent>30 || hardpercent>40){
        //      string[] difficulty = new string[] { "Medium", "Hard" };
        //      string randomdifficulty = difficulty[Random.Range(0, difficulty.Length)];
        //      return randomdifficulty;
        //  }
        //}
    }

    /// <summary>
    /// Changes the text of the item
    /// </summary>
    private void TextChange()
    {
        string difficulty = PickRandomDifficulty();
        changingText.text = difficulty;
    }
}

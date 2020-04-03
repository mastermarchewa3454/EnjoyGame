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
        // Get number of correct answered questions
        string str = PlayerPrefs.GetString("pastResults", null);
        Debug.Log(str);
        string[] arr = str.Split(',');

        string[] difficulty = new string[] { "Easy", "Medium", "Hard" };
        if (arr != null && int.Parse(arr[0]) > 3){
            int easyPercent = int.Parse(arr[1]);
            int mediumPercent = int.Parse(arr[2]);
            int hardPercent = int.Parse(arr[3]);
            if(easyPercent>30 || hardPercent>40){
                difficulty = new string[] { "Medium", "Hard" };
            }
        }
        string randomdifficulty = difficulty[Random.Range(0, difficulty.Length)];
        return randomdifficulty;
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

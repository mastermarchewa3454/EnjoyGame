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

    /// </summary>
    /// <returns>Difficulty</returns>
    private string PickRandom()
    {
        string[] difficulty = new string[] { "Easy", "Medium", "Hard" };
        string randomdifficulty = difficulty[Random.Range(0, difficulty.Length)];
        return randomdifficulty;
    }

    /// <summary>
    /// Changes the text of the item
    private void TextChange()
    {
        string difficulty = PickRandomDifficulty();
        changingText.text = difficulty;
    }
}

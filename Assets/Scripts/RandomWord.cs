using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class RandomWord : MonoBehaviour
{
    private Text changingText;

    // Start is called before the first frame update
    void Start()
    {
        changingText = gameObject.transform.GetChild(0).gameObject.GetComponent<Text>();
        TextChange();
    }

    public string PickRandomDifficulty()
    {
        string[] difficulty = new string[] { "Easy", "Medium", "Hard" };
        string randomdifficulty = difficulty[Random.Range(0, difficulty.Length)];
        return randomdifficulty;
    }

    
    public void TextChange()
    {
        string difficulty = PickRandomDifficulty();
        changingText.text = difficulty;
    }
}

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class RandomWord : MonoBehaviour
{
    public Text changingText;
    public GameObject changingTextdifficulty;
    // Start is called before the first frame update
    void Start()
    {
        TextChange();
    }

    public string PickRandom()
    {
        string[] difficulty = new string[] { "Easy", "Medium", "Hard" };
        string randomdifficulty = difficulty[Random.Range(0, difficulty.Length)];
        return randomdifficulty;
    }

    public void TextChange()
    {
        string difficulty = PickRandom();
        changingText.text = difficulty;
        changingTextdifficulty.GetComponent<Text>().text = difficulty;

    }
}

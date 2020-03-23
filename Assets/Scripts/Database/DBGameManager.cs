using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DBGameManager : DBManager
{
    public IEnumerator SaveResults(int[] results)
    {
        string score = "{\"score\":\"" + results[8] + "\"," +
                        "\"stageNumber\":\"" + PlayerPrefs.GetInt("stage", 1) + "\"," +
                        "\"easyCorrect\":\"" + results[0] + "\"," +
                        "\"mediumCorrect\":\"" + results[2] + "\"," +
                        "\"hardCorrect\":\"" + results[4] + "\"," +
                        "\"easyTotal\":\"" + results[0] + results[1] + "\"}," +
                        "\"mediumTotal\":\"" + results[2] + results[3] + "\"}," +
                        "\"hardTotal\":\"" + results[4] + results[5] + "\"}";
        yield return StartCoroutine(PostData("/users/"+userId+"/results", score));
        Debug.Log("Score submitted!");
    }
}

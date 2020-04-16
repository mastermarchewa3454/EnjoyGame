using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DBResultsManager : DBManager
{
    Result result;
    Result[] results;
    DuoResult[] duoResults;

    public IEnumerator SaveResults(int[] results)
    {
        string score = "{\"score\":\"" + results[8] + "\"," +
                        "\"stageNumber\":\"" + PlayerPrefs.GetInt("stage", 1).ToString() + "\"," +
                        "\"easyCorrect\":\"" + results[0] + "\"," +
                        "\"mediumCorrect\":\"" + results[2] + "\"," +
                        "\"hardCorrect\":\"" + results[4] + "\"," +
                        "\"easyTotal\":\"" + (results[0] + results[1]).ToString() + "\"," +
                        "\"mediumTotal\":\"" + (results[2] + results[3]).ToString() + "\"," +
                        "\"hardTotal\":\"" + (results[4] + results[5]).ToString() + "\"}";
        yield return StartCoroutine(PostData("/users/"+userId+"/results", score));
        Debug.Log("Score submitted!");
    }

    public IEnumerator SetMaxStage(int stage)
    {
        string stageData = "{\"maxStageCanPlay\":\"" + stage.ToString() + "\"}";
        yield return StartCoroutine(PostData("/users/" + userId + "/max-stage", stageData));
        Debug.Log("Max stage set");
    }

    public IEnumerator GetUserResults(int stage=-1)
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            if (stage == -1)
            {
                yield return StartCoroutine(GetData("/users/" + userId + "/summary-report-all", callback: data => resultString = data));
                results = JsonHelper.FromJson<Result>(resultString);
            }
            else {
                yield return StartCoroutine(GetData("/users/" + userId + "/summary-report?stageNumber=" + stage, callback: data => resultString = data));
                result = JsonUtility.FromJson<Result>(resultString);

                int easy = result.easyCorrect;
                int medium = result.mediumCorrect;
                int hard = result.hardCorrect;
                int total = easy + medium + hard;
                int easyPercent = easy / total * 100;
                int mediumPercent = medium / total * 100;
                int hardPercent = hard / total * 100;
                string[] arr = { total.ToString(), easyPercent.ToString(), mediumPercent.ToString(), hardPercent.ToString() };

                PlayerPrefs.SetString("pastResults", string.Join(",", arr));
            }
        }
    }

    public IEnumerator GetTop10(int index, System.Action<Result[]> callback)
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            yield return StartCoroutine(GetData("/results/top20?stageNumber="+index, callback: data => {
                resultString = data;
                Debug.Log(data);
            }));
            results = JsonHelper.FromJson<Result>(resultString);
            Debug.Log(results[0].score);
            callback(results);
        }
    }

    public IEnumerator GetDuoTop10(int index, System.Action<DuoResult[]> callback)
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            yield return StartCoroutine(GetData("/results/duo/top20?stageNumber="+index, callback: data => {
                resultString = data;
                Debug.Log(data);
            }));
            duoResults = JsonHelper.FromJson<DuoResult>(resultString);
            Debug.Log(duoResults[0].score);
            callback(duoResults);
        }
    }
}

[System.Serializable]
public class Result
{
    public string userId;
    public int score;
    public int stageNumber;
    public int easyCorrect;
    public int mediumCorrect;
    public int hardCorrect;
    public int easyTotal;
    public int mediumTotal;
    public int hardTotal;
}

[System.Serializable]
public class DuoResult
{
    public string userId1;
    public string userId2;
    public int score;
}
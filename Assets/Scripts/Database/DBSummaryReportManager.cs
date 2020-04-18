﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DBSummaryReportManager : DBManager
{
    public IEnumerator GetSummaryReport(string otherUserId, int stage, System.Action<Result> callback)
    {
        Result result;

        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            yield return StartCoroutine(GetData("/users/" + otherUserId + "/summary-report?stageNumber=" + stage, callback: data => resultString = data));
            result = JsonUtility.FromJson<Result>(resultString);
            callback(result);
        }
    }

    public IEnumerator GetSummaryReport(string otherUserId, System.Action<Result[]> callback)
    {
        Result[] results;

        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            yield return StartCoroutine(GetData("/users/" + otherUserId + "/summary-report-all", callback: data => resultString = data));
            results = JsonHelper.FromJson<Result>(resultString);
            callback(results);
        }
    }
}

[System.Serializable]
public class SumReport
{
    public string userId;
    public int stageNumber;
    public int easyCorrect;
    public int mediumCorrect;
    public int hardCorrect;
    public int easyTotal;
    public int mediumTotal;
    public int hardTotal;
}
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DBSummaryReportManager : DBManager
{
    public IEnumerator GetSummaryReport(string otherUserId, int stage, System.Action<SumReport> callback)
    {
        SumReport result;

        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            yield return StartCoroutine(GetData("/users/" + otherUserId + "/summary-report?stageNumber=" + stage, callback: data => resultString = data));
            result = JsonUtility.FromJson<SumReport>(resultString);
            callback(result);
        }
    }

    public IEnumerator GetSummaryReport(string otherUserId, System.Action<SumReport[]> callback)
    {
        SumReport[] results;

        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            yield return StartCoroutine(GetData("/users/" + otherUserId + "/summary-report-all", callback: data => resultString = data));
            results = JsonHelper.FromJson<SumReport>(resultString);
            callback(results);
        }
    }

    public IEnumerator GetClassSummaryReport(string otherUserId, System.Action<ClassSumReport[]> callback)
    {
        ClassSumReport[] results;

        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string resultString = "";
            yield return StartCoroutine(GetData("/classes/" + otherUserId + "/summary-report-all", callback: data => resultString = data));
            results = JsonHelper.FromJson<ClassSumReport>(resultString);
            callback(results);
        }
    }
}

[System.Serializable]
public class SumReport
{
    public string userId;
    public string username;
    public int stageNumber;
    public int easyCorrect;
    public int mediumCorrect;
    public int hardCorrect;
    public int easyTotal;
    public int mediumTotal;
    public int hardTotal;
}

[System.Serializable]
public class ClassSumReport
{
    public string classId;
    public string className;
    public int stageNumber;
    public int easyCorrect;
    public int mediumCorrect;
    public int hardCorrect;
    public int easyTotal;
    public int mediumTotal;
    public int hardTotal;
}
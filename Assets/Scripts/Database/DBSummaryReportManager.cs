using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// DB manager for summary report
/// </summary>
public class DBSummaryReportManager : DBManager
{
    /// <summary>
    /// Gets summary report of any user.
    /// </summary>
    /// <param name="otherUserId">userID of any user</param>
    /// <param name="stage">Stage number</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
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

    /// <summary>
    /// Gets summary report for a certain stage of any user.
    /// </summary>
    /// <param name="otherUserId">userID of any user</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
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

    /// <summary>
    /// Gets summary report for a class.
    /// </summary>
    /// <param name="otherUserId">classID of class</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
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

/// <summary>
/// SumReport class to parse JSON
/// </summary>
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

/// <summary>
/// ClassSumReport class to parse JSON
/// </summary>
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
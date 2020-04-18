using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class DisplayReport : MonoBehaviour
{
    public TMP_Text studentName;
    public TMP_Text studentClass;
    public TMP_Text maxLevel;

    DBSummaryReportManager db;

    Result[] user;

    public void Start()
    {
        db = GetComponent<DBSummaryReportManager>();
        StartCoroutine(GetStudentDetails());
    }


    
    IEnumerator GetStudentDetails()
    {
        string userId = PlayerPrefs.GetString("userId");
        Debug.Log(userId);
        yield return StartCoroutine(db.GetSummaryReport(userId, callback: data => user = data));

        studentName.text = userId;
        studentClass.text = "Class: " + PlayerPrefs.GetString("className");
        maxLevel.text = "Maximium Level Reached: " + user[0].stageNumber.ToString();

    }
}

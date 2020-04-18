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

    Result user;

    public void Start()
    {
        db = GetComponent<DBSummaryReportManager>();
        StartCoroutine(GetStudentDetails());
    }


    IEnumerator GetStudentDetails()
    {
        yield return StartCoroutine(db.GetUserResults(callback: data => user = data));

        studentName.text = user.userId;
        studentClass.text = PlayerPrefs.GetString("className");
        maxLevel.text = stageNumber;

    }
}

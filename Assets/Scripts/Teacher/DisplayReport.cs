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

    SumReport[] user;

    public void Start()
    {
        db = FindObjectOfType<DBSummaryReportManager>();
        StartCoroutine(GetStudentDetails());
    }

    IEnumerator GetStudentDetails()
    {
        int maxStage = 1;
        int easyCorrect = 0;
        int mediumCorrect = 0;
        int hardCorrect = 0;
        int easyTotal = 0;
        int mediumTotal = 0;
        int hardTotal = 0;

        string userId = PlayerPrefs.GetString("otherUserId");
        yield return StartCoroutine(db.GetSummaryReport(userId, callback: data => {
            studentName.text = "Student Name: " + PlayerPrefs.GetString("firstName");
            studentClass.text = PlayerPrefs.GetString("className");

            foreach (SumReport sr in data)
            {
                if (sr.stageNumber > maxStage)
                    maxStage = sr.stageNumber;

                easyCorrect += sr.easyCorrect;
                mediumCorrect += sr.mediumCorrect;
                hardCorrect += sr.hardCorrect;
                easyTotal += sr.easyTotal;
                mediumTotal += sr.mediumTotal;
                hardTotal += sr.hardTotal;
            }

            maxLevel.text = "Maximum Stage Reached: " + maxStage.ToString();
            int[] correct = { easyCorrect, mediumCorrect, hardCorrect };
            int[] total = { easyTotal, mediumTotal, hardTotal };
            GetCurrentFill(correct, total);
        }));
/*
            Debug.Log(data[0].userId);
            Debug.Log("Bye");
            studentName.text = data[0].userId;
            studentClass.text = PlayerPrefs.GetString("className");
            maxLevel.text = data[0].stageNumber.ToString();*/
    }

    public void GetCurrentFill(int[] current, int[] maximum)
    {
        int minimum = 0;
        Image mask;
        Image fill;
        Color color;
        Text experienceText;
        string[] radialBars = { "EasyRadialProgressBar", "MediumRadialProgressBar", "HardRadialProgressBar" };
        GameObject radialBar;

        for (int i=0; i<3; i++)
        {
            radialBar = GameObject.Find(radialBars[i]);
            GameObject goFill = radialBar.transform.GetChild(0).gameObject;
            mask = fill = goFill.GetComponent<Image>();
/*            GameObject goMask = goFill.transform.GetChild(0).gameObject;
            mask = goMask.GetComponent<Image>();*/
            experienceText = radialBar.transform.GetChild(1).GetComponent<Text>();

            string hexColor = "#F5A800";
            float currentOffset = current[i] - minimum;
            float maximumOffset = maximum[i] - minimum;

            float fillAmount = currentOffset / maximumOffset;
            mask.fillAmount = fillAmount;

            experienceText.text = currentOffset.ToString() + "/" + maximumOffset.ToString() + "\n Questions Correct";

            if (ColorUtility.TryParseHtmlString(hexColor, out color))
            {
                fill.color = color;
            }
        }
    }
}

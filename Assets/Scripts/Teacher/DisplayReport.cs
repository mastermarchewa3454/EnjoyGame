using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class DisplayReport : MonoBehaviour
{
    /// <summary>
    /// Instantiating a class of TMP_Text that will be used in unity of variable name studentName.
    /// This will be the name of user that is being searched on.
    /// </summary>
    public TMP_Text studentName;
    /// <summary>
    /// Instantiating a class of TMP_Text that will be used in unity of variable name studentClass
    /// This will be the name of student's class 
    /// </summary>
    public TMP_Text studentClass;
    /// <summary>
    /// Instantiating a class of TMP_Text that will be used in unity of variable maxLevel
    /// this will be maximum level he has reached.
    /// </summary>
    public TMP_Text maxLevel;

    /// <summary>
    /// Instantiating a DBSummaryReportManager of variable name db, that will call the DB. 
    /// </summary>
    DBSummaryReportManager db;
    /// <summary>
    /// Creating a variable name user of SumReport[] class that will contain all the relevant student's summary report.
    /// </summary>
    SumReport[] user;
    /// <summary>
    /// 
    /// </summary>
    public void Start()
    {
        db = FindObjectOfType<DBSummaryReportManager>();
        if (PlayerPrefs.GetString("firstName").Equals("View All")){
            StartCoroutine(GetClassDetails());
        }
        else
        {
            StartCoroutine(GetStudentDetails());
        }
    }
    /// <summary>
    /// This function will get the classdetails for the total number of correct and total number of questions.
    /// </summary>
    /// <returns></returns>
    IEnumerator GetClassDetails()
    {
        int maxStage = 1;
        int easyCorrect = 0;
        int mediumCorrect = 0;
        int hardCorrect = 0;
        int easyTotal = 0;
        int mediumTotal = 0;
        int hardTotal = 0;

        string classId = PlayerPrefs.GetString("otherUserId");
        yield return StartCoroutine(db.GetClassSummaryReport(classId, callback: data => {
            studentName.text = "Viewing summary report for";
            studentClass.text = PlayerPrefs.GetString("className");

            foreach (ClassSumReport sr in data)
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
    }
    /// <summary>
    /// This function will make use of IEnumerator that will connect with database so as to return the summary report 
    /// of the required student details.
    /// </summary>
    /// <returns>It goes through database and returns the total number correct and total number of questions</returns>
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
    }
/// <summary>
/// GetCurrentFill is a function that will be called so as to get the percentage of correctly answered questions 
/// over the total amount of questions answered.
/// </summary>
/// <param name="current">current is the parameter that will be the numerator used</param>
/// <param name="maximum">maximum will be the parameter that will be the denominator that will be used</param>
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

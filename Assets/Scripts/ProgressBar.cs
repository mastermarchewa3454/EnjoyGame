using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

#if UNITY_EDITOR
using UnityEditor;
#endif
/// <summary>
/// The progressbar class is to act as a class that contains scripts that will be able to display the number of questions that are answered correctly
/// versus the total amount of questions.
/// </summary>
[ExecuteInEditMode()]
public class ProgressBar : MonoBehaviour
{
#if UNITY_EDITOR

[MenuItem("GameObject/UI/Linear Progress Bar")]
public static void AddLinearProgressBar()
{
    GameObject obj = Instantiate(Resources.Load<GameObject>("UI/Linear Progess Bar"));
    obj.transform.SetParent(Selection.activeGameObject.transform, false);
}
#endif



    public int maximum;
    public int current; 
    public int minimum;
    public Image mask;
    public Image fill; 
    public Color color;

    public Text experienceText;


    /// <summary>
    /// An update function to display any changes and updates that were made. It will call the GetCurrentFill Function
    /// </summary>
    public void Update()
    {
        GetCurrentFill();
    }

    /// <summary>
    /// GetCurrentFill is a function that will update to show the total number of correctly answered questions by the total number of questions
    /// </summary>
    public void GetCurrentFill()
    {
        float currentOffset = current - minimum;
        float maximumOffset = maximum - minimum; 

        float fillAmount = currentOffset/maximumOffset;
        mask.fillAmount = fillAmount;

        experienceText.text = currentOffset.ToString() + "/" + maximumOffset.ToString();

        fill.color = color;
    }


}

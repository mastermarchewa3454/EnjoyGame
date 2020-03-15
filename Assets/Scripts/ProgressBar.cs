using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

#if UNITY_EDITOR
using UnityEditor;
#endif

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



    public void Update()
    {
        GetCurrentFill();
    }

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

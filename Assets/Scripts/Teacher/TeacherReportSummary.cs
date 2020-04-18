using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class TeacherReportSummary : MonoBehaviour
{
    public TMP_Dropdown dropdown;
    public TMP_Text teacherName;
    public TMP_Text className;

    // Start is called before the first frame update
    void Start()
    {
        PopulateList();

        teacherName.text = "hello";
        className.text = "3a";
    }

    public void PopulateList()
    {
        List<string> names = new List<string>()
        {
            "View All", "Betty", "Funny", "Hello" 
        };
        dropdown.AddOptions(names);
    }


}

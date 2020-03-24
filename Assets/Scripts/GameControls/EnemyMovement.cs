using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Script controlling enemy movement.
/// Enemy alternates between a few phases:
/// - Movement
/// - Stop
/// - Shoot
/// </summary>
public class EnemyMovement : MonoBehaviour
{
    [SerializeField]
    private float speed = 1;

    [SerializeField]
    private float movementInterval = 1;

    [SerializeField]
    private float stopInterval = 2;

    private Rigidbody2D rb;
    private Vector2 movement;
    private Animator anim;
    private FireController enemyFire;

    private bool moving = true;
    private bool attacking = false;
    private bool faceRight = false;

    /// <summary>
    /// Gets relevant components and initiates movement coroutine
    /// </summary>
    void Start()
    {
        anim = GameObject.Find("Sprite").GetComponent<Animator>();
        rb = gameObject.GetComponent<Rigidbody2D>();
        enemyFire = gameObject.GetComponent<FireController>();
        StartCoroutine(NewHeading());
    }

    /// <summary>
    /// Moves the enemy
    /// </summary>
    void FixedUpdate()
    {
        rb.MovePosition(rb.position + movement * speed * Time.fixedDeltaTime);
    }

    /// <summary>
    /// Controls the movement phases of the enemy
    /// </summary>
    /// <returns></returns>
    IEnumerator NewHeading()
    {
        while (true)
        {
            // Alternates between movement, stop, shoot phase
            anim.SetBool("isMoving", moving);
            anim.SetBool("isAttacking", attacking);

            if (moving)
            {
                // Sets a new direction to head in
                NewHeadingRoutine();
                moving = false;
                yield return new WaitForSeconds(movementInterval);
            }
            else
            {
                // Stops moving
                StopHeading();

                if (attacking)
                {
                    enemyFire.Shoot();
                    moving = true;
                }
                attacking = !attacking;
                yield return new WaitForSeconds(stopInterval / 2);
            }
        }
    }

    /// <summary>
    /// Stops the enemy from moving
    /// </summary>
    void StopHeading()
    {
        movement.x = 0;
        movement.y = 0;
    }

    /// <summary>
    /// Finds a new randome direction to move in
    /// </summary>
    void NewHeadingRoutine()
    {
        movement.x = Random.Range(-1f, 1f);
        movement.y = Random.Range(-1f, 1f);

        // Flip sprite if moving in opposite direction
        if ((movement.x > 0 && !faceRight) || (movement.x < 0 && faceRight))
        {
            rb.transform.localScale = new Vector2(rb.transform.localScale.x * -1, 1);
            faceRight = !faceRight;
        }
    }
}

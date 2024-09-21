package com.tl.mineminds.service.api

import com.tl.mineminds.MineMinds
import com.tl.mineminds.entity.LearningMaterial
import com.tl.mineminds.entity.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

object Api {

    const val BASE_URL = "https://4e25-2409-40f4-6-9a7c-7437-6671-ccf4-a5ec.ngrok-free.app/api"
    var USE_MOCK = true

    suspend fun fetchSubjectList(userToken: String): List<Subject> {
        val subjects = mutableListOf<Subject>()
        withContext(Dispatchers.IO) {
            try {
                if(USE_MOCK) throw Exception("Dont wait")
                val request = Request.Builder()
                    .url("${BASE_URL}courses")
                    .header("Authorization", "Bearer $userToken")
                    .build()
                val response = MineMinds.httpClient.newCall(request).execute()
                val responseData = response.body?.string()!!
                val responseJson = JSONObject(responseData)
                //convertJsonToSubjects()
                println(responseJson)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        subjects.add(Subject(1, "Maths", ""))
        subjects.add(Subject(2, "Physics", ""))
        subjects.add(Subject(3, "Chemistry", ""))
        subjects.add(Subject(4, "Biology", ""))
        return subjects
    }


    suspend fun fetchLearningMaterial(userToken: String): List<LearningMaterial> {
        val learningMaterials = mutableListOf<LearningMaterial>()
        withContext(Dispatchers.IO) {
            try {
                if(USE_MOCK) throw Exception("Dont wait")
                val request = Request.Builder()
                    .url("https://4e25-2409-40f4-6-9a7c-7437-6671-ccf4-a5ec.ngrok-free.app/api/levels")
                    //.url("${BASE_URL}levels")
                    .header("Authorization", "Bearer $userToken")
                    .build()
                val response = MineMinds.httpClient.newCall(request).execute()
                val responseData = response.body?.string()!!
                val responseJson = JSONObject(responseData)
                //convertJsonToSubjects()
                println(responseJson)
                val learningMaterialArray: JSONArray = responseJson.getJSONArray("data")

                // Iterate over the JSONArray and map each element to a Subject object
                for (i in 0 until learningMaterialArray.length()) {
                    val json = learningMaterialArray.getJSONObject(i)
                    val learningMaterial = LearningMaterial(
                        subjectId = json.getInt("courseId"),
                        lessonId = json.getInt("levelId"),
                        imageResUrl = json.getString("imageUrl"),
                        content = json.getString("content"),
                        title = json.getString("title"),
                    )
                    learningMaterials.add(learningMaterial)
                }
            } catch (e: Exception) {
                e.printStackTrace()


                val responseJson = JSONObject(rawDataForLearningMaterial)
                //convertJsonToSubjects()
                val learningMaterialArray: JSONArray = responseJson.getJSONArray("data")

                // Iterate over the JSONArray and map each element to a Subject object
                for (i in 0 until learningMaterialArray.length()) {
                    val json = learningMaterialArray.getJSONObject(i)
                    val learningMaterial = LearningMaterial(
                        subjectId = json.getInt("courseId"),
                        lessonId = json.getInt("levelId"),
                        imageResUrl = json.getString("imageUrl"),
                        content = json.getString("content"),
                        title = json.getString("title"),
                    )
                    learningMaterials.add(learningMaterial)
                }

            }
        }
        return learningMaterials
    }

    private val rawDataForLearningMaterial = "{\"data\":[{\"courseId\":1,\"levelId\":1,\"title\":\"Algebra Basics\",\"imageUrl\":\"https://trioltenergy.com/wp-content/uploads/2024/09/images-2.jpeg\",\"content\":\"\\n        **Algebra Basics**\\n\\n        Algebra is a branch of mathematics dealing with symbols and the rules for manipulating these symbols. In elementary algebra, these symbols (often represented by letters) represent numbers and are used to express general mathematical relationships. Algebra is widely used because it provides a systematic way to solve equations and understand the relationships between variables.\\n\\n        ### Key Concepts:\\n\\n        1. **Variables**: Symbols, usually letters like x, y, or z, that represent unknown values.\\n        2. **Constants**: Fixed values that do not change, such as numbers like 3, -5, or 1/2.\\n        3. **Expressions**: Combinations of variables, constants, and operations (such as addition and multiplication).\\n        4. **Equations**: Statements that two expressions are equal, often used to find the value of a variable.\\n\\n        ### Example:\\n        \\n        The equation of a straight line is often expressed as:\\n        \\n        \\\\[\\n        y = mx + c\\n        \\\\]\\n\\n        where:\\n        - \\\\( m \\\\) is the slope of the line.\\n        - \\\\( c \\\\) is the y-intercept, where the line crosses the y-axis.\\n\\n        Algebra plays a critical role in various real-life situations, such as financial modeling, engineering, and computer algorithms, making it a fundamental skill in mathematics and many applied sciences.\\n    \"},{\"courseId\":1,\"levelId\":2,\"title\":\"Linear Equations\",\"imageUrl\":\"https://arxiv.org/pdf/2204.00901.pdf\",\"content\":\"\\n        **Linear Equations**\\n\\n        A linear equation is an equation in which the highest power of the variable is one. Linear equations are called \\\"linear\\\" because they graph as straight lines when plotted on a coordinate plane. They are among the simplest and most widely used mathematical models.\\n\\n        ### Forms of Linear Equations:\\n\\n        1. **Standard Form**: \\n           \\\\[\\n           ax + by = c\\n           \\\\]\\n           where \\\\( a \\\\), \\\\( b \\\\), and \\\\( c \\\\) are constants.\\n        \\n        2. **Slope-Intercept Form**:\\n           \\\\[\\n           y = mx + c\\n           \\\\]\\n           where \\\\( m \\\\) is the slope of the line, and \\\\( c \\\\) is the y-intercept.\\n        \\n        3. **Point-Slope Form**:\\n           \\\\[\\n           y - y_1 = m(x - x_1)\\n           \\\\]\\n           where \\\\( (x_1, y_1) \\\\) is a point on the line and \\\\( m \\\\) is the slope.\\n\\n        ### Applications:\\n\\n        - **Physics**: Used to describe uniform motion.\\n        - **Economics**: Used in cost and revenue functions.\\n        - **Statistics**: Helps in modeling relationships between variables.\\n\\n        Solving linear equations is fundamental in mathematics, providing the basis for more complex topics such as linear algebra and calculus. They are used in algorithms, predictive modeling, and optimization problems.\\n    \"},{\"courseId\":1,\"levelId\":3,\"title\":\"Geometry\",\"imageUrl\":\"https://trioltenergy.com/wp-content/uploads/2024/09/images.png\",\"content\":\"\\n        **Geometry**\\n\\n        Geometry is a branch of mathematics concerned with the properties, measurement, and relationships of points, lines, angles, surfaces, and solids. It provides tools to model and analyze the spatial properties of objects and is foundational in fields such as architecture, engineering, and art.\\n\\n        ### Key Concepts:\\n\\n        1. **Points and Lines**: The most basic elements of geometry; a point has no dimension, while a line extends infinitely in both directions.\\n        2. **Angles**: Formed by two intersecting lines, measured in degrees or radians.\\n        3. **Shapes and Polygons**: Closed figures such as triangles, squares, and circles.\\n        4. **Solids**: Three-dimensional objects like cubes, spheres, and cylinders.\\n\\n        ### Formulas:\\n\\n        1. **Area of a Circle**:\\n\\n           \\\\[\\n           A = \\\\pi r^2\\n           \\\\]\\n\\n           where \\\\( r \\\\) is the radius of the circle.\\n\\n        2. **Pythagorean Theorem**: In a right triangle:\\n\\n           \\\\[\\n           c^2 = a^2 + b^2\\n           \\\\]\\n\\n           where \\\\( c \\\\) is the hypotenuse, and \\\\( a \\\\) and \\\\( b \\\\) are the other two sides.\\n\\n        3. **Volume of a Sphere**:\\n\\n           \\\\[\\n           V = \\\\frac{4}{3} \\\\pi r^3\\n           \\\\]\\n\\n        ### Applications:\\n\\n        - **Architecture**: Designing buildings and structures.\\n        - **Engineering**: Analyzing forces and designing mechanical components.\\n        - **Computer Graphics**: Modeling 3D objects and environments.\\n\\n        Geometry helps us understand and manipulate the spatial dimensions of objects, making it a crucial tool in both theoretical and applied sciences.\\n    \"},{\"courseId\":1,\"levelId\":4,\"title\":\"Statistics\",\"imageUrl\":\"https://trioltenergy.com/wp-content/uploads/2024/09/images.png\",\"content\":\"\\n        **Statistics**\\n\\n        Statistics is a branch of mathematics that deals with the collection, analysis, interpretation, presentation, and organization of data. It is essential for making informed decisions based on empirical data and is used in virtually every field, from economics to biology.\\n\\n        ### Key Concepts:\\n\\n        1. **Descriptive Statistics**: Summarizes data using measures such as mean, median, mode, and standard deviation.\\n        2. **Inferential Statistics**: Draws conclusions from data samples about a population, often using hypothesis testing and confidence intervals.\\n        3. **Probability Distributions**: Describes how the values of a random variable are distributed (e.g., Normal, Binomial, Poisson distributions).\\n        4. **Regression Analysis**: Explores the relationship between variables and is used for predictive modeling.\\n\\n        ### Key Formulas:\\n\\n        1. **Mean (Average)**:\\n\\n           \\\\[\\n           \\\\bar{x} = \\\\frac{\\\\sum_{i=1}^{n} x_i}{n}\\n           \\\\]\\n\\n           where \\\\( x_i \\\\) are the observed values, and \\\\( n \\\\) is the number of observations.\\n\\n        2. **Standard Deviation**:\\n\\n           \\\\[\\n           \\\\sigma = \\\\sqrt{\\\\frac{\\\\sum_{i=1}^{n} (x_i - \\\\bar{x})^2}{n}}\\n           \\\\]\\n\\n        3. **Correlation Coefficient**:\\n\\n           \\\\[\\n           r = \\\\frac{\\\\sum (x - \\\\bar{x})(y - \\\\bar{y})}{\\\\sqrt{\\\\sum (x - \\\\bar{x})^2 \\\\sum (y - \\\\bar{y})^2}}\\n           \\\\]\\n\\n        ### Applications:\\n\\n        - **Healthcare**: Analyzing patient data and clinical trials.\\n        - **Business**: Market research and consumer behavior analysis.\\n        - **Government**: Census data and public policy analysis.\\n\\n        Statistics provides the tools needed to understand data and make decisions based on quantitative evidence, making it invaluable in research, industry, and daily life.\\n    \"},{\"courseId\":1,\"levelId\":5,\"title\":\"Probability\",\"imageUrl\":\"https://trioltenergy.com/wp-content/uploads/2024/09/images.png\",\"content\":\"\\n        **Probability**\\n\\n        Probability is a branch of mathematics that deals with the likelihood of different outcomes in uncertain situations. It quantifies the uncertainty of events and helps predict future events based on historical data. Probability theory forms the basis for statistics, gambling, decision-making, and many fields in science and engineering.\\n\\n        ### Key Concepts:\\n\\n        1. **Experiment**: A process that leads to one of several possible outcomes.\\n        2. **Sample Space (S)**: The set of all possible outcomes of an experiment.\\n        3. **Event (E)**: A subset of the sample space; a specific outcome or set of outcomes.\\n        4. **Probability of an Event (P(E))**: A measure of the likelihood that an event will occur, given by:\\n\\n           \\\\[\\n           P(E) = \\\\frac{\\\\text{Number of favorable outcomes}}{\\\\text{Total number of outcomes}}\\n           \\\\]\\n\\n        ### Example:\\n\\n        If you roll a fair six-sided die, the probability of rolling a 4 is:\\n\\n        \\\\[\\n        P(4) = \\\\frac{1}{6}\\n        \\\\]\\n\\n        ### Key Formulas:\\n\\n        1. **Addition Rule**: For any two events \\\\( A \\\\) and \\\\( B \\\\):\\n\\n           \\\\[\\n           P(A \\\\cup B) = P(A) + P(B) - P(A \\\\cap B)\\n           \\\\]\\n\\n        2. **Multiplication Rule**: For independent events \\\\( A \\\\) and \\\\( B \\\\):\\n\\n           \\\\[\\n           P(A \\\\cap B) = P(A) \\\\times P(B)\\n           \\\\]\\n\\n        3. **Conditional Probability**: The probability of event \\\\( A \\\\) given that event \\\\( B \\\\) has occurred:\\n\\n           \\\\[\\n           P(A | B) = \\\\frac{P(A \\\\cap B)}{P(B)}\\n           \\\\]\\n\\n        ### Applications:\\n\\n        - **Statistics**: Used to interpret survey results and experimental data.\\n        - **Machine Learning**: To build predictive models.\\n        - **Finance**: In risk assessment and decision-making.\\n\\n        Probability helps us understand randomness, make predictions, and make informed decisions in everyday life and professional fields.\\n    \"}]}"

}
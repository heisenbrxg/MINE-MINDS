

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.zoho.android.calendarsdk.entities.enums.attendee.AttendeeStatus
import com.zoho.android.calendarsdk.entities.model.user.UserAccountInfo
import com.zoho.android.calendarsdk.feature.notification.R
import com.zoho.android.calendarsdk.feature.notification.ZCNotificationCore
import com.zoho.android.calendarsdk.feature.notification.data.model.NotificationArgs
import com.zoho.android.calendarsdk.feature.notification.ui.theme.ZCNotificationNTheme
import com.zoho.android.calendarsdk.feature.notification.ui.theme.ZCNotificationTheme
import com.zoho.android.calendarsdk.feature.notification.ui.viewmodel.NotificationViewModel
import com.zoho.android.calendarsdk.feature.notification.utils.NotificationUtils
import com.zoho.android.calendarsdk.ui.utils.extension.rippleClickable
import com.zoho.android.calendarsdk.ui.widget.ZCTopAppBarSurface
import com.zoho.android.calendarsdk.ui.widget.attendeestatus.EventSelfStatus
import com.zoho.android.calendarsdk.util.CalendarHelper
import com.zoho.shared.calendar.resources.SharedRes
import com.zoho.shared.calendarsdk.api.notification.data.model.NotificationResponse
import com.zoho.shared.calendarsdk.resources.compose.calendarStringResource
import com.zoho.shared.calendarsdk.resources.compose.style.ZCalendarDimens
import dev.icerock.moko.resources.StringResource

@Composable
fun NotificationScreen(
    navController: NavController,
    notificationArgs: NotificationArgs,
) {
    val zcNotificationViewModel =
        viewModel<NotificationViewModel>(factory = ZCNotificationCore.zcNotificationViewModelFactory)

    val userAccountInfo = remember(notificationArgs) {
        notificationArgs.userAccountInfo
    }
    val notifications = zcNotificationViewModel.notificationsData.collectAsLazyPagingItems()

    LaunchedEffect(userAccountInfo) {
        if (userAccountInfo != null) {
            zcNotificationViewModel.loadNotifications(userAccountInfo)
        }
    }

    if (userAccountInfo != null) {
        ZCNotificationContainer(
            userAccountInfo,
            zcNotificationViewModel,
            notifications,
            navController,
            notificationArgs.notificationPermissionEnabled
        )
    }
}

@Composable
private fun ZCNotificationContainer(
    userAccountInfo: UserAccountInfo,
    viewModel: NotificationViewModel,
    notifications: LazyPagingItems<NotificationResponse>,
    navController: NavController,
    notificationPermissionEnabled: Boolean
) {
    Scaffold(backgroundColor = ZCNotificationNTheme.colors.background,
        scaffoldState = rememberScaffoldState(),
        modifier = Modifier
            .imePadding(),
        topBar = {
            ZCNotificationTopAppBar()
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(bottom = ZCalendarDimens.defaultPadding)
        ) {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ZCalendarDimens.defaultMarginHalf)
            )

            PermissionBannerCard()

            LazyColumn(
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(notifications.itemSnapshotList.items.size) { index ->
                    NotificationCard(
                        notification = notifications.itemSnapshotList.items[index],
                        navController
                    )
                }

                notifications.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val e = notifications.loadState.refresh as LoadState.Error
                            item {
                                Text("Error: ${e.error.message}")
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun ZCNotificationTopAppBar() {
    ZCTopAppBarSurface(
        ZCNotificationNTheme.colors.background,
        ZCNotificationNTheme.colors.notificationTopAppBarColor
    ) {
        TopAppBar(backgroundColor = it, elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .background(it)
                .statusBarsPadding(),
            title = {
                Text(
                    text = calendarStringResource(resource = SharedRes.strings.notification),
                    style = ZCNotificationNTheme.typography.title,
                    color = ZCNotificationNTheme.colors.appBarTitleColor
                )
            })
    }
}

@Composable
fun PermissionBannerCard() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        // Background shadow card
        Card(
            shape = RoundedCornerShape(size = ZCalendarDimens.size10),
            elevation = 0.dp, backgroundColor = Color(0xFFB0C4DE),
            modifier = Modifier
                .padding(top = 6.dp, start = 25.dp, end = 8.dp)
                .fillMaxWidth()
                .height(180.dp),
        ) {
            // Empty content, just to give the background shadow effect
        }

        PermissionBannerCardContainer(
            modifier = Modifier,
            titleResId = SharedRes.strings.title_notification_permission,
            descriptionResId = SharedRes.strings.desc_notification_permission,
            buttonResId = SharedRes.strings.label_enable,
            backgroundColor = ZCNotificationNTheme.colors.colorAccent,
            iconColor = ZCNotificationNTheme.colors.iconColor,
            enableButtonBackgroundColor = ZCNotificationNTheme.colors.iconColor,
            titleStyle = ZCNotificationNTheme.typography.bannerTitle,
            descriptionStyle = ZCNotificationNTheme.typography.bannerDescription,
            enableButtonStyle = ZCNotificationNTheme.typography.enableButton,
            skipButtonStyle = ZCNotificationNTheme.typography.skipButton,
            onEnableClick = { /*TODO*/ },
            onSkipClick = { /*TODO*/ }) {
        }

    }
}

@Composable
fun PermissionBannerCardContainer(
    modifier: Modifier, titleResId: StringResource, descriptionResId: StringResource,
    buttonResId: StringResource, backgroundColor: Color, iconColor: Color,
    enableButtonBackgroundColor: Color,
    titleStyle: TextStyle, descriptionStyle: TextStyle,
    enableButtonStyle: TextStyle, skipButtonStyle: TextStyle, onEnableClick: () -> Unit,
    onSkipClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    Card(
        elevation = 0.dp, backgroundColor = backgroundColor,
        modifier = modifier
            .padding(
                horizontal = ZCalendarDimens.defaultMargin,
                vertical = ZCalendarDimens.defaultMargin
            )
            .fillMaxWidth(), shape = RoundedCornerShape(size = ZCalendarDimens.size10)
    ) {
        ConstraintLayout(modifier = Modifier.padding(ZCalendarDimens.defaultPadding)) {
            val (titleText, removeIcon, description, enableButton, skipButton) = createRefs()

            Text(
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(removeIcon.start)
                    width = Dimension.fillToConstraints

                },
                text = calendarStringResource(resource = titleResId),
                style = titleStyle,
                color = ZCNotificationNTheme.colors.bannerTitle
            )

            Text(modifier = Modifier
                .constrainAs(description) {
                    top.linkTo(titleText.bottom)
                    start.linkTo(titleText.start)
                    end.linkTo(titleText.end)
                    width = Dimension.fillToConstraints
                }
                .padding(top = ZCalendarDimens.margin12),
                text = calendarStringResource(resource = descriptionResId),
                style = descriptionStyle,
                color = ZCNotificationNTheme.colors.bannerDescription
            )

            Icon(painter = painterResource(id = R.drawable.ic_close),
                contentDescription = calendarStringResource(resource = SharedRes.strings.close_button),
                tint = iconColor, modifier = Modifier
                    .padding(start = ZCalendarDimens.defaultMargin)
                    .constrainAs(removeIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)

                    }
                    .rippleClickable {
                        onRemoveClick()
                    })


            TextButton(
                onClick = {
                    onEnableClick()
                }, modifier = Modifier
                    .padding(top = ZCalendarDimens.defaultMargin)
                    .constrainAs(enableButton) {
                        top.linkTo(description.bottom)
                        start.linkTo(parent.start)

                    }, shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = enableButtonBackgroundColor),
                contentPadding = PaddingValues(horizontal = ZCalendarDimens.padding28)
            ) {
                Text(
                    text = calendarStringResource(resource = buttonResId),
                    style = enableButtonStyle
                )
            }

            TextButton(onClick = {
                onSkipClick()
            }, modifier = Modifier
                .padding(top = ZCalendarDimens.defaultMargin)
                .constrainAs(skipButton) {
                    top.linkTo(enableButton.top)
                    bottom.linkTo(enableButton.bottom)
                    start.linkTo(enableButton.end)
                    height = Dimension.fillToConstraints

                }) {
                Text(
                    text = calendarStringResource(resource = SharedRes.strings.skip),
                    style = skipButtonStyle
                )
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationResponse, navController: NavController) {

    val resource = LocalContext.current.resources

    val isUnread = remember { mutableStateOf(notification.isRead) }

    Card(
        elevation = 0.dp, backgroundColor = ZCNotificationNTheme.colors.cardBackground,
        modifier = Modifier
            .padding(
                horizontal = ZCalendarDimens.defaultPadding, vertical = ZCalendarDimens.margin6
            )
            .fillMaxWidth(), shape = ZCNotificationNTheme.shapes.notificationCardShape
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {

                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(notification.info.image)
                        .size(Size.ORIGINAL) // Set the target size to load the image at.
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = calendarStringResource(resource = SharedRes.strings.notification),
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(0.dp, Color.Gray, CircleShape)
                )


                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    val title = if (notification.info.updated) {
                        " " + calendarStringResource(SharedRes.strings.has_updated_an_event) + " "
                    } else {
                        " " + calendarStringResource(SharedRes.strings.has_invited_to_an_event) + " "
                    }

                    val styledText = buildAnnotatedString {
                        // Apply a style to "Jetpack Compose"
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append("${notification.info.name}")
                        }
                        append(title)

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append("${notification.info.title}")
                        }

                        append(
                            " @ ${
                                NotificationUtils.getFormattedDateTime(
                                    startDateTime = notification.info.startTime,
                                    endDateTime = notification.info.endTime,
                                    currentTimeZoneId = null,
                                    requiredTimeZoneId = null
                                )
                            }"
                        )

                    }

                    Text(
                        text = styledText,
                        style = TextStyle(
                            color = ZCNotificationNTheme.colors.title,
                            fontSize = 16.sp
                        ),
                        lineHeight = 25.sp
                    )

                    Text(
                        text = CalendarHelper.formatDate(
                            timeInMillis = notification.time,
                            requiredFormat = NotificationUtils.NOTIFICATION_RECEIVED_DATE_FORMAT,
                            isBestDateTimePatternConversionEnabled = true
                        ) + " " + CalendarHelper.formatDate(
                            timeInMillis = notification.time,
                            requiredFormat = NotificationUtils.DEFAULT_TIME_FORMAT_12_HOUR,
                            isBestDateTimePatternConversionEnabled = true
                        ),
                        style = ZCNotificationNTheme.typography.receivedTime,
                        color = ZCNotificationNTheme.colors.receivedTime,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    if (!notification.info.isExpired) {
                        val attendeeStatus = remember {
                            mutableStateOf(getAttendeeStatus(notification.info.localAttendeeStatus))
                        }
                        EventSelfStatus(
                            eventStatusState = attendeeStatus.value,
                            backgroundColor = ZCNotificationNTheme.colors.bottomAppBarBackgroundColor,
                            unSelectedBorderColor = ZCNotificationNTheme.colors.divider,
                            unSelectedFillColor = ZCNotificationNTheme.colors.bottomAppBarBackgroundColor,
                            getSelectedBorderColor = {
                                getBorderColor(attendeeStatus = it)
                            },
                            getSelectedFillColor = {
                                getFillColor(attendeeStatus = it)
                            },
                            statusContainerShape = ZCNotificationNTheme.shapes.statusContainer,
                            statusTextStyle = ZCNotificationNTheme.typography.statusText
                                .copy(color = ZCNotificationNTheme.colors.title),
                            needChildHorizontalPadding = false,
                            enableEdgeToEdge = true,
                            onAttendeeStatusChanged = {
                                attendeeStatus.value = it
                            })
                    }
                }
            }
        }
    }
}

fun getAttendeeStatus(localAttendeeStatus: Int): AttendeeStatus {
    return when (localAttendeeStatus) {
        1 -> AttendeeStatus.ACCEPTED
        2 -> AttendeeStatus.DECLINED
        3 -> AttendeeStatus.TENTATIVE
        else -> {
            AttendeeStatus.NEEDS_ACTION
        }
    }
}

@Composable
fun getBorderColor(attendeeStatus: AttendeeStatus): Color {

    return when (attendeeStatus) {

        AttendeeStatus.ACCEPTED -> {
            ZCNotificationNTheme.colors.acceptedStrokeColor
        }

        AttendeeStatus.DECLINED -> {
            ZCNotificationNTheme.colors.declinedStrokeColor
        }

        AttendeeStatus.TENTATIVE -> {
            ZCNotificationNTheme.colors.tentativeStrokeColor
        }

        else -> {
            ZCNotificationNTheme.colors.bannerTitle
        }

    }
}


@Composable
fun getFillColor(attendeeStatus: AttendeeStatus): Color {

    return when (attendeeStatus) {

        AttendeeStatus.ACCEPTED -> {
            ZCNotificationNTheme.colors.acceptedFillColor
        }

        AttendeeStatus.DECLINED -> {
            ZCNotificationNTheme.colors.declinedFillColor
        }

        AttendeeStatus.TENTATIVE -> {
            ZCNotificationNTheme.colors.tentativeFillColor
        }

        else -> {
            ZCNotificationNTheme.colors.bottomAppBarBackgroundColor
        }

    }
}







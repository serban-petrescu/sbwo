package spet.sbwo.data;

import static org.h2.api.ErrorCode.*;

import java.util.Optional;

import org.h2.jdbc.JdbcSQLException;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final DatabaseError error;
	private final String details;

	public DatabaseException(Throwable cause) {
		Optional<JdbcSQLException> vendorException = getVendorException(cause);
		if (vendorException.isPresent()) {
			this.error = getErrorFromH2Code(vendorException.get().getErrorCode());
			this.details = vendorException.get().getMessage();
		} else {
			this.error = DatabaseError.OTHER;
			this.details = "";
		}
	}

	public DatabaseException(DatabaseError error, String details) {
		this.error = error;
		this.details = details;
	}

	public DatabaseError getError() {
		return error;
	}

	public String getDetails() {
		return details;
	}

	private static Optional<JdbcSQLException> getVendorException(Throwable cause) {
		while (cause != null) {
			if (cause instanceof JdbcSQLException) {
				return Optional.of((JdbcSQLException) cause);
			}
			cause = cause.getCause();
		}
		return Optional.empty();
	}

	private static DatabaseError getErrorFromH2Code(int code) {
		switch (code) {
		case DRIVER_VERSION_ERROR_2:
		case INVALID_DATABASE_NAME_1:
		case ERROR_OPENING_DATABASE_1:
		case EXCEPTION_OPENING_PORT_2:
		case COMPRESSION_ERROR:
		case DATABASE_ALREADY_OPEN_1:
		case DATABASE_CALLED_AT_SHUTDOWN:
		case DATABASE_IS_CLOSED:
		case DATABASE_IS_IN_EXCLUSIVE_MODE:
		case DATABASE_IS_NOT_PERSISTENT:
		case DATABASE_IS_READ_ONLY:
		case DATABASE_NOT_FOUND_1:
		case CONNECTION_BROKEN_1:
		case CANNOT_DROP_CURRENT_USER:
		case CANNOT_CHANGE_SETTING_WHEN_OPEN_1:
			return DatabaseError.CONNECTION_ERROR;

		case VIEW_ALREADY_EXISTS_1:
		case VIEW_IS_INVALID_2:
		case VIEW_NOT_FOUND_1:
		case TABLE_OR_VIEW_ALREADY_EXISTS_1:
		case TABLE_OR_VIEW_NOT_FOUND_1:
		case SEQUENCE_ALREADY_EXISTS_1:
		case SEQUENCE_ATTRIBUTES_INVALID:
		case SEQUENCE_BELONGS_TO_A_TABLE_1:
		case SEQUENCE_EXHAUSTED:
		case SEQUENCE_NOT_FOUND_1:
		case ROLE_ALREADY_EXISTS_1:
		case ROLE_ALREADY_GRANTED_1:
		case ROLE_CAN_NOT_BE_DROPPED_1:
		case ROLE_NOT_FOUND_1:
		case INVALID_PARAMETER_COUNT_2:
		case FUNCTION_ALIAS_ALREADY_EXISTS_1:
		case FUNCTION_ALIAS_NOT_FOUND_1:
		case FUNCTION_MUST_RETURN_RESULT_SET_1:
		case FUNCTION_NOT_FOUND_1:
		case INDEX_BELONGS_TO_CONSTRAINT_2:
		case INDEX_NOT_FOUND_1:
		case INDEX_ALREADY_EXISTS_1:
		case ERROR_CREATING_TRIGGER_OBJECT_3:
		case DUPLICATE_COLUMN_NAME_1:
		case CONSTANT_ALREADY_EXISTS_1:
		case CONSTANT_NOT_FOUND_1:
		case CONSTRAINT_ALREADY_EXISTS_1:
		case CONSTRAINT_NOT_FOUND_1:
		case COLUMN_COUNT_DOES_NOT_MATCH:
		case COLUMN_IS_PART_OF_INDEX_1:
		case COLUMN_IS_REFERENCED_1:
		case COLUMN_MUST_NOT_BE_NULLABLE_1:
		case COLUMN_NOT_FOUND_1:
		case CANNOT_DROP_2:
		case CANNOT_DROP_LAST_COLUMN:
		case CANNOT_DROP_TABLE_1:
		case CANNOT_MIX_INDEXED_AND_UNINDEXED_PARAMS:
		case CANNOT_TRUNCATE_1:
		case AGGREGATE_NOT_FOUND_1:
		case AMBIGUOUS_COLUMN_NAME_1:
			return DatabaseError.STRUCTURE_ERROR;

		case NOT_ENOUGH_RIGHTS_FOR_1:
		case ACCESS_DENIED_TO_CLASS_1:
		case ADMIN_RIGHTS_REQUIRED:
			return DatabaseError.AUTHORIZATION_ERROR;

		case DUPLICATE_KEY_1:
		case DUPLICATE_PROPERTY_1:
		case CHECK_CONSTRAINT_INVALID:
		case CHECK_CONSTRAINT_VIOLATED_1:
			return DatabaseError.CONSTRAINT_VIOLATED;

		case STRING_FORMAT_ERROR_1:
		case VALUE_TOO_LONG_2:
		case NULL_NOT_ALLOWED:
		case NUMERIC_VALUE_OUT_OF_RANGE_1:
		case INVALID_DATETIME_CONSTANT_2:
		case INVALID_TO_CHAR_FORMAT:
		case INVALID_TO_DATE_FORMAT:
		case INVALID_VALUE_2:
		case INVALID_VALUE_SCALE_PRECISION:
		case HEX_STRING_ODD_1:
		case HEX_STRING_WRONG_1:
		case COLUMN_CONTAINS_NULL_VALUES_1:
			return DatabaseError.INVALID_VALUE;

		case CONCURRENT_UPDATE_1:
		case DEADLOCK_1:
			return DatabaseError.CONCURRENT_ACCESS;

		case DIVISION_BY_ZERO_1:
			return DatabaseError.DIVISION_BY_ZERO;

		case CAN_ONLY_ASSIGN_TO_VARIABLE_1:
		case CLASS_NOT_FOUND_1:
		case CLUSTER_ERROR_DATABASE_RUNS_ALONE:
		case CLUSTER_ERROR_DATABASE_RUNS_CLUSTERED_1:
		case COLLATION_CHANGE_WITH_DATA_TABLE_1:
		case COMMIT_ROLLBACK_NOT_ALLOWED:
		case DATA_CONVERSION_ERROR_1:
		case DESERIALIZATION_FAILED_1:
		case ERROR_ACCESSING_LINKED_TABLE_2:
		case ERROR_EXECUTING_TRIGGER_3:
		case ERROR_SETTING_DATABASE_EVENT_LISTENER_2:
		case EXCEPTION_IN_FUNCTION_1:
		case FEATURE_NOT_SUPPORTED_1:
		case FILE_CORRUPTED_1:
		case FILE_CREATION_FAILED_1:
		case FILE_DELETE_FAILED_1:
		case FILE_ENCRYPTION_ERROR_1:
		case FILE_NOT_FOUND_1:
		case FILE_RENAME_FAILED_2:
		case FILE_VERSION_ERROR_1:
		case GENERAL_ERROR_1:
		case INVALID_CLASS_2:
		case INVALID_USE_OF_AGGREGATE_FUNCTION_1:
		case IO_EXCEPTION_1:
		case IO_EXCEPTION_2:
		case JAVA_OBJECT_SERIALIZER_CHANGE_WITH_DATA_TABLE:
		case LIKE_ESCAPE_ERROR_1:
		case LITERALS_ARE_NOT_ALLOWED:
		case LOB_CLOSED_ON_TIMEOUT_1:
		case LOCK_TIMEOUT_1:
		case METHODS_MUST_HAVE_DIFFERENT_PARAMETER_COUNTS_2:
		case METHOD_NOT_ALLOWED_FOR_PREPARED_STATEMENT:
		case METHOD_NOT_ALLOWED_FOR_QUERY:
		case METHOD_NOT_FOUND_1:
		case METHOD_ONLY_ALLOWED_FOR_QUERY:
		case MUST_GROUP_BY_COLUMN_1:
		case NOT_ON_UPDATABLE_ROW:
		case NO_DATA_AVAILABLE:
		case NO_DEFAULT_SET_1:
		case OBJECT_CLOSED:
		case ORDER_BY_NOT_IN_RESULT:
		case OUT_OF_MEMORY:
		case PARAMETER_NOT_SET_1:
		case PARSE_ERROR_1:
		case PUBLIC_STATIC_JAVA_METHOD_NOT_FOUND_1:
		case REFERENTIAL_INTEGRITY_VIOLATED_CHILD_EXISTS_1:
		case REFERENTIAL_INTEGRITY_VIOLATED_PARENT_MISSING_1:
		case REMOTE_CONNECTION_NOT_ALLOWED:
		case RESULT_SET_NOT_SCROLLABLE:
		case RESULT_SET_NOT_UPDATABLE:
		case RESULT_SET_READONLY:
		case ROLES_AND_RIGHT_CANNOT_BE_MIXED:
		case ROW_NOT_FOUND_WHEN_DELETING_1:
		case SAVEPOINT_IS_INVALID_1:
		case SAVEPOINT_IS_NAMED:
		case SAVEPOINT_IS_UNNAMED:
		case SCALAR_SUBQUERY_CONTAINS_MORE_THAN_ONE_ROW:
		case SCHEMA_ALREADY_EXISTS_1:
		case SCHEMA_CAN_NOT_BE_DROPPED_1:
		case SCHEMA_NAME_MUST_MATCH:
		case SCHEMA_NOT_FOUND_1:
		case SECOND_PRIMARY_KEY:
		case SERIALIZATION_FAILED_1:
		case STATEMENT_WAS_CANCELED:
		case STEP_SIZE_MUST_NOT_BE_ZERO:
		case SUBQUERY_IS_NOT_SINGLE_COLUMN:
		case SUM_OR_AVG_ON_WRONG_DATATYPE_1:
		case SYNTAX_ERROR_1:
		case SYNTAX_ERROR_2:
		case TRACE_CONNECTION_NOT_CLOSED:
		case TRACE_FILE_ERROR_2:
		case TRANSACTION_NOT_FOUND_1:
		case TRIGGER_ALREADY_EXISTS_1:
		case TRIGGER_NOT_FOUND_1:
		case TRIGGER_SELECT_AND_ROW_BASED_NOT_SUPPORTED:
		case UNKNOWN_DATA_TYPE_1:
		case UNKNOWN_MODE_1:
		case UNSUPPORTED_CIPHER:
		case UNSUPPORTED_COMPRESSION_ALGORITHM_1:
		case UNSUPPORTED_COMPRESSION_OPTIONS_1:
		case UNSUPPORTED_LOCK_METHOD_1:
		case UNSUPPORTED_OUTER_JOIN_CONDITION_1:
		case UNSUPPORTED_SETTING_1:
		case UNSUPPORTED_SETTING_COMBINATION:
		case URL_FORMAT_ERROR_2:
		case URL_RELATIVE_TO_CWD:
		case USER_ALREADY_EXISTS_1:
		case USER_DATA_TYPE_ALREADY_EXISTS_1:
		case USER_DATA_TYPE_NOT_FOUND_1:
		case USER_NOT_FOUND_1:
		case USER_OR_ROLE_NOT_FOUND_1:
		case WRONG_PASSWORD_FORMAT:
		case WRONG_USER_OR_PASSWORD:
		case WRONG_XID_FORMAT_1:
		default:
			return DatabaseError.OTHER;
		}
	}
}
